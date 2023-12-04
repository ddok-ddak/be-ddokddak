package com.ddokddak.category.service;

import com.ddokddak.category.domain.dto.*;
import com.ddokddak.category.domain.entity.Category;
import com.ddokddak.category.domain.entity.CategoryIcon;
import com.ddokddak.category.repository.CategoryIconRepository;
import com.ddokddak.category.repository.CategoryJdbcRepository;
import com.ddokddak.category.repository.CategoryRepository;
import com.ddokddak.common.exception.CustomApiException;
import com.ddokddak.common.exception.NotValidRequestException;
import com.ddokddak.common.exception.type.BaseException;
import com.ddokddak.common.exception.type.CategoryException;
import com.ddokddak.common.exception.type.CategoryIconException;
import com.ddokddak.common.exception.type.MemberException;
import com.ddokddak.member.domain.entity.Member;
import com.ddokddak.member.domain.enums.TemplateType;
import com.ddokddak.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.ddokddak.category.mapper.CategoryMapper.fromCategoryAddRequest;

@Service
@RequiredArgsConstructor
public class CategoryWriteService {

    private final CategoryRepository categoryRepository;
    private final CategoryJdbcRepository categoryJdbcRepository;
    private final MemberRepository memberRepository;
    private final CategoryIconRepository categoryIconRepository;


    @Transactional
    public CategoryAddResponse addCategory(Long memberId, CategoryAddRequest req) {

        Member member = memberRepository.findById( memberId )
                .orElseThrow(() -> new CustomApiException( MemberException.MEMBER_ID ));

        CategoryIcon categoryIcon = this.categoryIconRepository.getReferenceById(req.iconId());

        Category category = null;
        Long createdCategoryId  = null;

        /*
            1. 대분류 등록일 경우
            대분류들간의 중복 허용 X
        */
        if( Objects.equals(req.level(),0) ){
            // 요청 대분류 카테고리명과 멤버 아이디를 이용해서 기 카테고리명 유무 조회
            if( categoryRepository.existsByLevelAndNameAndMemberIdAndIsDeletedFalse( req.level(), req.name(), member.getId() ) ){
                throw new CustomApiException(CategoryException.USED_NAME_CONFLICTS);
            }

            category = fromCategoryAddRequest(req, member, null, categoryIcon);
            createdCategoryId = categoryRepository.save( category ).getId();
        }

        /*
            2. 소분류(서브 카테고리 중 하나) 등록일 경우
            유효한 대분류 카테고리 아래에서 소분류들간의 중복 허용 X
            (단, 소분류들간의 중복되지 않는 한에서 대분류명과는 중복 가능)
        */
        else if( Objects.equals(req.level(),1) ){
            /* 서브 카테고리인 경우의 검증 수행 */
            // - 상위 카테고리 정보가 없는 경우 예외 발생
            if( req.mainCategoryId() == null || req.mainCategoryId()==0 ){
                throw new CustomApiException( BaseException.NULL_DATA );
            }

            // - 메인 카테고리 아이디의 유효성 검증
            var mainCategory = categoryRepository.findByIdAndMemberIdAndIsDeletedFalse( req.mainCategoryId(), member.getId() )
                    .orElseThrow( () -> new CustomApiException(CategoryException.MAIN_CATEGORY_ID) );

            // - 요청 대분류, 소분류 카테고리명과 멤버 아이디를 이용해서 소분류 카테고리의 기 카테고리명 유무 조회
            if( Boolean.TRUE.equals(categoryRepository.existsByNameAndMainCategoryIdAndMemberIdAndIsDeletedFalse(req.name(), mainCategory.getId(),  member.getId())) ){
                throw new CustomApiException(CategoryException.USED_NAME_CONFLICTS);
            }

            category = fromCategoryAddRequest(req, member, mainCategory, categoryIcon);
            createdCategoryId = categoryRepository.save(category).getId();
        }

        return new CategoryAddResponse(createdCategoryId);
    }

    /**
     * 카테고리 삭제
     * 소프트 딜리트 수행하여, is_deleted 컬럼의 값 '1'(Boolean.True)로 변경
     * @param category 카테고리 아이디
     * @param memberId 멤버 아이디
     */
    @Transactional
    public void removeCategoryByIdAndMemberId(Category category, Long memberId) {

        categoryRepository.softDelete(category);
    }

    /**
     * 카테고리의 이름, 아이콘 파일명 값을 변경하고자 하는 경우
     * @param req CategoryValueModifyRequest 카테고리 값 수정 요청 정보
     * @param memberId 멤버 아이디
     */
    @Transactional
    public void modifyCategoryValue(ModifyCategoryValueRequest req, Long memberId) {

        // 카테고리 아이디와 멤버 아이디로 조회
        var category = categoryRepository.findByIdAndMemberIdAndIsDeletedFalse(req.categoryId(), memberId)
                .orElseThrow(() -> new NotValidRequestException(CategoryException.CATEGORY_ID));

        var isEqualName = Objects.equals(req.name(), category.getName());
//        var isEqualColor = Objects.equals(req.color(), category.getColor());
        var isEqualIconName = Objects.equals(req.iconId(), category.getIconFile().getId());

        if (isEqualName && isEqualIconName) { // isEqualColor &&
            throw new CustomApiException(BaseException.UNABLE_REQUEST);
        }
        if (!isEqualName) {
            // 이름 검증 후 변경
            List<Category> categories = this.getCategoriesToCompareName(category, category.getMainCategory());
            this.checkCategoryNameAbleToUse(req.name(), category.getId(), categories);
            category.modifyName(req.name());
        }
//        if (!isEqualColor) category.modifyColor(req.color());
        var iconFile = this.categoryIconRepository.getReferenceById(req.iconId());
        //findByIdAndIsDeletedFalse(req.iconId())
        //        .orElseThrow(() -> new CustomApiException(CategoryIconException.ICON_ID));

        if (!isEqualIconName) category.modifyIconFile(iconFile);
    }

    /**
     * 카테고리 이름 변경 작업 시,
     * 같은 레벨 및 부모를 가진 카테고리 내에 동일한 이름이 있는지 확인한다. (자기 자신은 제외)
     * 그룹의 높이가 1인 경우만 고려한다. (즉, 메인과 서브 레벨만 존재한다.)
     *
     * @param reqName 변경할 카테고리의 새 이름
     * @param categoryId 대상 카테고리 아이디
     * @param categories 비교할 카테고리 리스트
     */
    private void checkCategoryNameAbleToUse(String reqName, Long categoryId, List<Category> categories) {

        var result = categories
                .stream()
                .filter(el -> !el.getId().equals(categoryId))
                .map(Category::getName)
                .anyMatch(nameCompared -> nameCompared.equals(reqName));
        if (result) {
            throw new NotValidRequestException(CategoryException.USED_NAME_CONFLICTS);
        }
    }

    /**
     * 카테고리 이름 변경 작업 시,
     * 비교할 대상 카테고리 그룹을 조회한다.
     *
     * @param category 현재 대상 카테고리
     * @param mainCategory 카테고리의 메인 카테고리
     * @return List<Category> 비교 대상 카테고리 목록
     */
    private List<Category> getCategoriesToCompareName(Category category, Category mainCategory) {

        List<Category> categories;
        if (Objects.isNull(mainCategory)) {
            // 대분류인 경우, 다른 대분류와 비교한다.
            categories = categoryRepository.findByMemberIdAndLevelAndIsDeleted(category.getMember().getId(), 0, Boolean.FALSE);
        } else {
            // 한 그룹의 모든 카테고리
            // 서브 카테고리와 메인 카테고리는 이름이 같아도 된다.
            categories = mainCategory.getSubCategories();
        }
        return categories;
    }

    @Transactional
    public void addCategoryTemplate(CategoryTemplateRequest req, Long memberId) {
        var values = req.templateType().getTemplates();
        categoryJdbcRepository.batchInsert(values, memberId);
    }

    @Transactional
    public void modifyCategoryTemplate(CategoryTemplateRequest req, Long memberId, TemplateType previousTemplateType) {

        // 전체 카테고리를 조회해와서 확인 후 제거 및 업데이트 수행
        var categories = categoryRepository.findByMemberIdAndLevel(memberId, 0);

        // 기존 템플릿에서 학생이면 학업, 직장이면 업무 카테고리 그룹 제거
        var pValues = previousTemplateType.getTemplates();
        var mainCategory = pValues.stream()
                .filter(v->v.getParentName()==null)
                .findFirst()
                .orElseThrow(() -> new NotValidRequestException(BaseException.NULL_DATA));

        categories.stream()
                .filter(category -> !category.getIsDeleted() && category.getName() == mainCategory.getName())
                .forEach(category-> category.deleteGroup());

        // 학생이면 학업, 직장이면 업무 카테고리 그룹 추가
        var newValues = req.templateType().getTemplates();
        var newMainCategory = newValues.stream()
                .filter(v->v.getParentName()==null)
                .findFirst()
                .orElseThrow(() -> new NotValidRequestException(BaseException.NULL_DATA));

        // 기존에 대분류가 존재했었다면(삭제 상태라면)
        var alreadyExistsCategory = categories.stream()
                .filter(category -> category.getName() == newMainCategory.getName())
                .findFirst();
        if (alreadyExistsCategory.isPresent()) {
            alreadyExistsCategory.get().undeleteGroup();
            return;
        }

        // 대분류 카테 갯수 제한
        if (categories.size() > 8) {
            throw new NotValidRequestException(BaseException.UNABLE_REQUEST);
        }
        categoryJdbcRepository.batchInsert(newValues, memberId);
    }


    // 아래는 사용 없는 코드 - 제거 예정
    /**
     * 카테고리의 관계, 레벨만을 변경하고자 하는 경우
     *
     * @param req CategoryRelationModifyRequest 카테고리 관계 수정 요청 정보
     * @param memberId 멤버 아이디
     */
    @Transactional
    public void modifyCategoryRelation(ModifyCategoryRelationRequest req, Long memberId) {
        // 카테고리 아이디와 멤버 아이디로 조회
        var category = categoryRepository.findByIdAndMemberIdAndIsDeletedFalse(req.categoryId(), memberId)
                .orElseThrow(() -> new NotValidRequestException(CategoryException.CATEGORY_ID));
        Category mainCategory = null;

        // 1. 메인 카테고리로 변경하는 경우
        // - 변경이 없다면, 요청이 올바르지 않아 예외 발생
        if (Objects.equals(req.level(), 0) && (
                Objects.equals(category.getLevel(), 0) ||
                        !(Objects.isNull(req.mainCategoryId()) || Objects.equals(req.mainCategoryId(), 0L)) )) {
            throw new NotValidRequestException(BaseException.IRONIC_REQUEST);
        }

        // 2. 서브 카테고리로 변경 및 유지
        // - 메인 카테고리가 달라지는 경우
        if (Objects.equals(req.level(), 1)) {

            this.validateSubCategoryRelation(category, req.mainCategoryId());
            mainCategory = this.checkMainCategoryToChange(req.mainCategoryId(), memberId);

            // 이동할 카테고리 그룹에 중복되는 이름이 있는지 검증
            List<Category> categories = this.getCategoriesToCompareName(category, mainCategory);
            this.checkCategoryNameAbleToUse(category.getName(), category.getId(), categories);
        }

        category.modifyCategoryRelation(req.level(), mainCategory);
    }

    @Transactional
    public void modifyCategory(ModifyCategoryRequest req, Long memberId) {
        // 카테고리 아이디와 멤버 아이디로 조회
        var category = categoryRepository.findByIdAndMemberIdAndIsDeletedFalse(req.categoryId(), memberId)
                .orElseThrow(() -> new NotValidRequestException(CategoryException.CATEGORY_ID));
        Category mainCategory = category.getMainCategory();

        // 1. 관계가 변경되지 않는 경우
        if (Objects.equals(category.getLevel(), req.level())
                && Objects.equals(mainCategory == null ? null : mainCategory.getId(), req.mainCategoryId())) {

            // 이름 변경이 있다면, 검증 수행
            if (!Objects.equals(req.name(), category.getName())) {
                // 이동할 카테고리 그룹에 중복되는 이름이 있는지 검증
                List<Category> categories = this.getCategoriesToCompareName(category, mainCategory);
                this.checkCategoryNameAbleToUse(req.name(), category.getId(), categories);
            }
        } else {
            // 2. 관계가 변경되는 경우
            // 2-1. 서브 카테고리가 메인 카테고리가 되는 경우 (req.level() == 0)
            // req.mainCategoryId() 값을 갖는 경우(not Null && not 0L) 예외 발생
            if (req.level() == 0 && (Objects.nonNull(req.mainCategoryId()) && !Objects.equals(req.mainCategoryId(), 0L))) {
                throw new CustomApiException(BaseException.IRONIC_REQUEST);
            }

            // 2-2. 메인 카데고리가 서브 카테고리가 되는 경우 (category.getLevel() == 0 && req.level() == 1 )
            // 2-3. 서브 카테고리를 유지 (category.getLevel() == 1 && req.level() == 1)
            // - 부모가 바뀌는 경우 (category.getMainCategoryId() != req.mainCategoryId())
            if (req.level() == 1) {
                this.validateSubCategoryRelation(category, req.mainCategoryId());
                mainCategory = this.checkMainCategoryToChange(req.mainCategoryId(), memberId);
            }

            List<Category> categories = this.getCategoriesToCompareName(category, mainCategory);
            this.checkCategoryNameAbleToUse(req.name(), category.getId(), categories);
        }

        category.modifyCategoryRelation(req.level(), mainCategory);
        category.modifyName(req.name());
        category.modifyColor(req.color());
    }

    /**
     * 요청에서 카테고리 레벨이 1인 경우 검증 수행
     * (req.level == 1)
     */
    private void validateSubCategoryRelation(Category category, Long reqMainCategoryId) {

        // 1-0. 상위 카테고리 정보가 없는 경우 예외 발생
        if (Objects.isNull(reqMainCategoryId)) {
            throw new CustomApiException(BaseException.NULL_DATA);
        }

        // 1-1. 서브 카테고리로 변경
        // - 기존에 메인 카테고리 && 서브 카테고리를 갖고 있는 경우 변경 불가하므로 예외 발생
        if (Objects.equals(category.getLevel(), 0) && !category.getSubCategories().isEmpty()) {
            throw new CustomApiException(BaseException.UNABLE_REQUEST);
        }

        // 1-2. 서브 카테고리로 유지
        // - 메인 카테고리 아이디가 이전과 동일한 경우
        if (Objects.equals(category.getLevel(), 1) && Objects.equals(category.getMainCategory().getId(), reqMainCategoryId)) {
            throw new CustomApiException(CategoryException.MAIN_CATEGORY_ID);
        }
    }

    /**
     * 메인 카테고리 검증
     * - 서브 카테고리 유지 및 메인 카테고리 변경시
     * - 서브 카테고리로 변경시
     * @param reqMainCategoryId 변경하고자 하는 메인 카테고리 아이디
     * @param memberId 멤버 아이디
     * @return Category 변경하고자 하는 메인 카테고리
     */
    private Category checkMainCategoryToChange(Long reqMainCategoryId, Long memberId) {

        return categoryRepository.findByIdAndMemberIdAndIsDeletedFalse(reqMainCategoryId, memberId)
                .orElseThrow(() ->
                        new CustomApiException(CategoryException.MAIN_CATEGORY_ID));
    }

    public void deleteAllByMainCategory(Category category) {
        categoryRepository.deleteAllByMainCategory(category);
    }
}
