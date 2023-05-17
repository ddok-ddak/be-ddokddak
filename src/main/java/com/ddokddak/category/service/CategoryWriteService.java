package com.ddokddak.category.service;

import com.ddokddak.activityRecord.repository.ActivityRecordJdbcRepository;
import com.ddokddak.category.dto.*;
import com.ddokddak.category.entity.Category;
import com.ddokddak.category.enums.BaseTemplate;
import com.ddokddak.category.enums.CategoryTemplate;
import com.ddokddak.category.repository.CategoryJdbcRepository;
import com.ddokddak.category.repository.CategoryRepository;
import com.ddokddak.common.exception.CustomApiException;
import com.ddokddak.common.exception.NotValidRequestException;
import com.ddokddak.common.exception.type.NotValidRequest;
import com.ddokddak.member.entity.Member;
import com.ddokddak.member.entity.TemplateType;
import com.ddokddak.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CategoryWriteService {

    private final CategoryRepository categoryRepository;
    private final CategoryJdbcRepository categoryJdbcRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public CategoryAddResponse addCategory(Long memberId, CategoryAddRequest req) {
        Member member = memberRepository.findById( memberId ).orElseThrow(
                () -> new NotValidRequestException( NotValidRequest.MEMBER_ID )
        );

        Category category = null;
        Long createdCategoryId  = null;

        /*
            1. 대분류 등록일 경우
            대분류들간의 중복 허용 X
        */
        if( Objects.equals(req.level(),0) ){
            // 요청 대분류 카테고리명과 멤버 아이디를 이용해서 기 카테고리명 유무 조회
            if( categoryRepository.existsByLevelAndNameAndMemberId( req.level(), req.name(), member.getId() ) ){
                throw new NotValidRequestException(NotValidRequest.USED_NAME_CONFLICTS);
            }

            category = Category.builder()
                                .name( req.name() )
                                .color( req.color() )
                                .level( req.level() )
                                .member( member )
                                .build();

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
                throw new NotValidRequestException( NotValidRequest.NULL_DATA );
            }

            // - 메인 카테고리 아이디의 유효성 검증
            var mainCategory = categoryRepository.findByIdAndMemberId( req.mainCategoryId(), member.getId() )
                    .orElseThrow( () -> new NotValidRequestException(NotValidRequest.MAIN_CATEGORY_ID) );

            // - 요청 대분류, 소분류 카테고리명과 멤버 아이디를 이용해서 소분류 카테고리의 기 카테고리명 유무 조회
            if( Boolean.TRUE.equals(categoryRepository.existsByNameAndMainCategoryIdAndMemberId(req.name(), mainCategory.getId(),  member.getId())) ){
                throw new NotValidRequestException(NotValidRequest.USED_NAME_CONFLICTS);
            }

            category = Category.builder()
                    .name( req.name() )
                    .color( req.color() )
                    .level( req.level() )
                    .mainCategory( mainCategory )
                    .member( member )
                    .build();

            createdCategoryId = categoryRepository.save(category).getId();
        }

        return new CategoryAddResponse(createdCategoryId);
    }

    /**
     * 카테고리 삭제
     * 소프트 딜리트 수행하여, delete_yn 컬럼의 값 'Y'로 변경
     * @param categoryId 카테고리 아이디
     * @param memberId 멤버 아이디
     */
    @Transactional
    public void removeCategoryByIdAndMemberId(Long categoryId, Long memberId) {
        var category = categoryRepository.findByIdAndMemberId(categoryId, memberId)
                .orElseThrow(() -> new NotValidRequestException(NotValidRequest.CATEGORY_ID));

        // 메인 카테고리이면, 서브 카테고리도 삭제 확인
        // 즉, 자식이 있을 경우 함께 삭제
        if (category.getLevel().equals(0)) categoryRepository.deleteAllByMainCategory(category);
        categoryRepository.delete(category);
    }

    /**
     * 카테고리의 이름, 색상 값만을 변경하고자 하는 경우
     * @param req CategoryValueModifyRequest 카테고리 값 수정 요청 정보
     * @param memberId 멤버 아이디
     */
    @Transactional
    public void modifyCategoryValue(ModifyCategoryValueRequest req, Long memberId) {

        // 카테고리 아이디와 멤버 아이디로 조회
        var category = categoryRepository.findByIdAndMemberId(req.categoryId(), memberId)
                .orElseThrow(() -> new NotValidRequestException(NotValidRequest.CATEGORY_ID));

        var isEqualName = Objects.equals(req.name(), category.getName());
        var isEqualColor = Objects.equals(req.color(), category.getColor());

        if (isEqualName && isEqualColor) {
            throw new CustomApiException(NotValidRequest.UNABLE_REQUEST);
        }
        if (!isEqualName) {
            // 이름 검증 후 변경
            List<Category> categories = this.getCategoriesToCompareName(category, category.getMainCategory());
            this.checkCategoryNameAbleToUse(req.name(), category.getId(), categories);
            category.modifyName(req.name());
        }
        if (!isEqualColor) {
            category.modifyColor(req.color());
        }
    }

    /**
     * 카테고리의 관계, 레벨만을 변경하고자 하는 경우
     * @param req CategoryRelationModifyRequest 카테고리 관계 수정 요청 정보
     * @param memberId 멤버 아이디
     */
        @Transactional
        public void modifyCategoryRelation(ModifyCategoryRelationRequest req, Long memberId) {
            // 카테고리 아이디와 멤버 아이디로 조회
            var category = categoryRepository.findByIdAndMemberId(req.categoryId(), memberId)
                .orElseThrow(() -> new NotValidRequestException(NotValidRequest.CATEGORY_ID));
        Category mainCategory = null;

        // 1. 메인 카테고리로 변경하는 경우
        // - 변경이 없다면, 요청이 올바르지 않아 예외 발생
        if (Objects.equals(req.level(), 0) && (
                Objects.equals(category.getLevel(), 0) ||
                        !(Objects.isNull(req.mainCategoryId()) || Objects.equals(req.mainCategoryId(), 0L)) )) {
            throw new NotValidRequestException(NotValidRequest.IRONIC_REQUEST);
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
        var category = categoryRepository.findByIdAndMemberId(req.categoryId(), memberId)
                .orElseThrow(() -> new NotValidRequestException(NotValidRequest.CATEGORY_ID));
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
                throw new NotValidRequestException(NotValidRequest.IRONIC_REQUEST);
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
     * 메인 카테고리 검증
     * - 서브 카테고리 유지 및 메인 카테고리 변경시
     * - 서브 카테고리로 변경시
     * @param reqMainCategoryId 변경하고자 하는 메인 카테고리 아이디
     * @param memberId 멤버 아이디
     * @return Category 변경하고자 하는 메인 카테고리
     */
    private Category checkMainCategoryToChange(Long reqMainCategoryId, Long memberId) {

        return categoryRepository.findByIdAndMemberId(reqMainCategoryId, memberId)
                .orElseThrow(() ->
                        new NotValidRequestException(NotValidRequest.MAIN_CATEGORY_ID));
    }

    /**
     * 카테고리 이름 변경 작업 시,
     * 같은 레벨 및 부모를 가진 카테고리 내에 동일한 이름이 있는지 확인한다. (자기 자신은 제외)
     * 그룹의 높이가 1인 경우만 고려한다. (즉, 메인과 서브 레벨만 존재한다.)
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
            throw new NotValidRequestException(NotValidRequest.USED_NAME_CONFLICTS);
        }
    }

    /**
     * 카테고리 이름 변경 작업 시,
     * 비교할 대상 카테고리 그룹을 조회한다.
     * @param category 현재 대상 카테고리
     * @param mainCategory 카테고리의 메인 카테고리
     * @return List<Category> 비교 대상 카테고리 목록
     */
    private List<Category> getCategoriesToCompareName(Category category, Category mainCategory) {

        List<Category> categories;
        if (Objects.isNull(mainCategory)) {
            // 대분류인 경우, 다른 대분류와 비교한다.
            categories = categoryRepository.findByMemberIdAndLevel(category.getMember().getId(), 0);
        } else {
            // 한 그룹의 모든 카테고리
            // 서브 카테고리와 메인 카테고리는 이름이 같아도 된다.
            categories = mainCategory.getSubCategories();
        }
        return categories;
    }

    /**
     * 요청에서 카테고리 레벨이 1인 경우 검증 수행
     * (req.level == 1)
     */
    private void validateSubCategoryRelation(Category category, Long reqMainCategoryId) {

        // 1-0. 상위 카테고리 정보가 없는 경우 예외 발생
        if (Objects.isNull(reqMainCategoryId)) {
            throw new NotValidRequestException(NotValidRequest.NULL_DATA);
        }

        // 1-1. 서브 카테고리로 변경
        // - 기존에 메인 카테고리 && 서브 카테고리를 갖고 있는 경우 변경 불가하므로 예외 발생
        if (Objects.equals(category.getLevel(), 0) && !category.getSubCategories().isEmpty()) {
            throw new NotValidRequestException(NotValidRequest.UNABLE_REQUEST);
        }

        // 1-2. 서브 카테고리로 유지
        // - 메인 카테고리 아이디가 이전과 동일한 경우
        if (Objects.equals(category.getLevel(), 1) && Objects.equals(category.getMainCategory().getId(), reqMainCategoryId)) {
            throw new NotValidRequestException(NotValidRequest.MAIN_CATEGORY_ID);
        }
    }

    @Transactional
    public void addCategoryTemplate(AddCategoryTemplateRequest req, Long memberId) {
        var member = memberRepository.findById(memberId)
                .orElseThrow(()->new CustomApiException(NotValidRequest.MEMBER_ID));
        var templateType = req.templateType();
        if (!member.getTemplateType().equals(TemplateType.NONE)) {
            // 타입을 변경하려면, 멤버 수정 api 활용
            throw new CustomApiException(NotValidRequest.ALREADY_EXISTS);
        }
        member.registerTemplateType(req.templateType());
//        var categoryTemplateJdbcDtoList = Arrays.stream(CategoryTemplate.values())
//                //.filter(categoryEnum -> categoryEnum.getParentName()==null)
//                .map(categoryEnum -> {
//                    return CategoryTemplateJdbcDto.builder()
//                            .name(categoryEnum.getName())
//                            .color(categoryEnum.getColor())
//                            .level(categoryEnum.getParentName() == null ? 0 : 1)
//                            .memberId(memberId)
//                            .mainCategoryName(categoryEnum.getParentName())
//                            .build();
//                })
//                .toList();
        var values = templateType.getTemplates();
        categoryJdbcRepository.batchInsert(values, memberId);
    }
}
