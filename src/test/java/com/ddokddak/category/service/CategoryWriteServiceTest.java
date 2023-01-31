package com.ddokddak.category.service;

import com.ddokddak.DatabaseCleanUp;
import com.ddokddak.category.dto.CategoryAddRequest;
import com.ddokddak.category.dto.CategoryModifyRequest;
import com.ddokddak.category.dto.CategoryRelationModifyRequest;
import com.ddokddak.category.dto.CategoryValueModifyRequest;
import com.ddokddak.category.entity.Category;
import com.ddokddak.category.fixture.CategoryFixture;
import com.ddokddak.category.repository.CategoryRepository;
import com.ddokddak.common.exception.NotValidRequestException;
import com.ddokddak.member.Member;
import com.ddokddak.member.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@AutoConfigureMockMvc
@SpringBootTest
//@Transactional // 롤백 수행 <- 레이지 로딩이 동작하지 않음
class CategoryWriteServiceTest {

    @Autowired
    CategoryWriteService categoryWriteService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    DatabaseCleanUp databaseCleanUp;

    private Member member;
    private List<Category> mainCategories;
    private List<Category> subCategories;

    @BeforeEach
    void setUp() {
        this.member = Member.builder()
                                .build();
        memberRepository.save(member);

        this.mainCategories = CategoryFixture.createMainCategories(0, 4, member);
        categoryRepository.saveAll(mainCategories);
        // 전달하는 숫자 파라미터에 따라 이름이 정해짐
        this.subCategories = CategoryFixture.createSubCategories(4, 8, member, mainCategories.get(2));
        categoryRepository.saveAll(subCategories);
    }

    @AfterEach
    void tearDown() {
        databaseCleanUp.afterPropertiesSet();
        databaseCleanUp.execute();
        // 레포지토리를 이용해 데이터를 삭제하고자 하는 경우에는 소프트 딜리트를 사용하므로 따로 테스트용 딜리트 메소드가 필요하다.
    }

    @DisplayName("대분류 카테고리 추가일 경우, 카테고리명과 멤버아이디 중복 여부에 대한 검증")
    @Test
    void occurExceptionForMainCategoryNameConflictsWithOthersWhenCreating(){
        // given, when
        CategoryAddRequest duplicatedRequest = CategoryAddRequest.builder()
                                                                .name("category0")
                                                                .color("color0")
                                                                .level(0)
                                                                .mainCategoryId(null)
                                                                .memberId(member.getId())
                                                                .build();
        // then
        assertThatThrownBy( ()-> categoryWriteService.addCategory( duplicatedRequest ) )
                .isInstanceOf( NotValidRequestException.class )
                .hasMessage("Already Used Name");
    }

    @DisplayName("소분류 카테고리 추가일 경우, 해당 회원 아이디에 존재하는 메인 카테고리 아이디의 유효성 검증")
    @Test
    void occurExceptionForMainCategoryIdValidation(){
        // given
        Category notValidatedMainCategory = Category.builder()
                                .id(111L)
                                .name("category11")
                                .color("color11")
                                .level(0)
                                .member(member)
                                .deleteYn("N")
                                .build();

        // when
        CategoryAddRequest duplicatedRequest = CategoryAddRequest.builder()
                .name("category00")
                .color("color0")
                .level(1)
                .mainCategoryId( notValidatedMainCategory.getId() ) // DB에 없는 아이디
                .memberId(member.getId())
                .build();

        // then
        assertThatThrownBy( ()-> categoryWriteService.addCategory( duplicatedRequest ) )
                .isInstanceOf( NotValidRequestException.class )
                .hasMessage("Not Valid Main Category Id");
    }

    @DisplayName("소분류 카테고리 추가일 경우, 해당 회원 아이디와 대분류 아래에 서브 카테고리명 중복 여부에 대한 검증")
    @Test
    void occurExceptionForSubCategoryNameValidationWithMemberIdAndMainCategory(){
        // given
        CategoryAddRequest duplicatedRequest = CategoryAddRequest.builder()
                .name("category4") // category2에 이미 있는 서브카테고리명
                .color("color4")
                .level(1)
                .mainCategoryId( mainCategories.get(2).getId() )
                .memberId( member.getId() )
                .build();

        // when
        assertThatThrownBy( ()-> categoryWriteService.addCategory( duplicatedRequest ) )
                .isInstanceOf( NotValidRequestException.class )
                .hasMessage("Already Used Name");
    }

    @DisplayName("정상적인 대분류 카테고리 등록")
    @Test
    void addMainCategory(){
        // given
        CategoryAddRequest request = CategoryAddRequest.builder()
                .name("category11")
                .color("color11")
                .level(0)
                .memberId(member.getId())
                .build();

        // when
        var createdCategoryId = categoryWriteService.addCategory( request );

        // then
        Assertions.assertTrue( categoryRepository.existsByLevelAndNameAndMemberId(request.level(),"category11",member.getId()) );
    }

    @DisplayName("정상적인 소분류 카테고리 등록")
    @Test
    void addSubCategory(){
        // given
        CategoryAddRequest request = CategoryAddRequest.builder()
                .name("category10")
                .color("color10")
                .level(1)
                .mainCategoryId(mainCategories.get(2).getId())
                .memberId(member.getId())
                .build();

        // when
        var createdCategoryId = categoryWriteService.addCategory( request );

        // then
        Assertions.assertTrue(
                categoryRepository.existsByNameAndMainCategoryIdAndMemberId("category10",mainCategories.get(2).getId(),member.getId())
        );
    }

    @DisplayName("상위 카테고리 삭제시 자식 카테고리까지 잘 삭제되는지")
    @Test
    void removeAllRelatedCategoryById() {
        // given
        var mainCategoryId = mainCategories.get(2).getId();

        // when
        categoryWriteService.removeCategoryByIdAndMemberId(mainCategoryId, member.getId());

        // then
        var idList = this.subCategories.stream()
                .map(category -> category.getId())
                .toList();
        var deleteStatusList = categoryRepository.findAllById(idList)
                .stream()
                .map(category -> category.getDeleteYn())
                .toList();

        Assertions.assertEquals(0, deleteStatusList.size());

    }

    @DisplayName("메인 카테고리의 이름을 변경할 때 현재 회원의 다른 메인 카테고리에 동일한 이름이 있으면 예외 발생")
    @Test
    void occurExceptionForMainCategoryNameConflictsWithOthers() {
        // given
        var mainCategory = mainCategories.get(2);
        var request = CategoryValueModifyRequest.builder()
                .categoryId(mainCategory.getId())
                .name(mainCategories.get(1).getName())
                .color(mainCategory.getColor())
                .build();

        // when, then
        assertThatThrownBy(()->categoryWriteService.modifyCategoryValue(request, member.getId()))
                .isInstanceOf(NotValidRequestException.class);

    }

    @DisplayName("서브 카테고리의 이름을 변경할 때 연관된 카테고리에 동일한 이름이 있으면 예외 발생")
    @Test
    void occurExceptionForSubCategoryNameConflictsWithOthers() {
        // given
        var subCategory = subCategories.get(3);
        var request = CategoryValueModifyRequest.builder()
                .categoryId(subCategory.getId())
                .name(subCategories.get(2).getName())
                .color(subCategory.getColor())
                .build();

        // when, then
        assertThatThrownBy(()->categoryWriteService.modifyCategoryValue(request, member.getId()))
                .isInstanceOf(NotValidRequestException.class);

    }

    @DisplayName("서브 카테고리를 메인 레벨로 변경할 때 잘 변경되는지")
    @Test
    void modifyCategoryWell() {
        // given
        var subCategory = subCategories.get(3);
        var request = CategoryRelationModifyRequest.builder()
                .categoryId(subCategory.getId())
                .level(0)
                .build();

        // when
        categoryWriteService.modifyCategoryRelation(request, subCategory.getMember().getId());

        // then
        var category = categoryRepository.findById(subCategory.getId())
                .orElseThrow();
        Assertions.assertEquals(0, category.getLevel());
        Assertions.assertEquals(null, category.getMainCategory());
    }

    @DisplayName("메인 카테고리를 서브 레벨로 변경할 때 서브 카테고리를 가지고 있다면 예외 발생")
    @Test
    void failToModifyMainCategoryToSub() {
        // given
        var mainCategoryWithSub = mainCategories.get(2);
        var request = CategoryRelationModifyRequest.builder()
                .categoryId(mainCategoryWithSub.getId())
                .level(1)
                .mainCategoryId(mainCategories.get(3).getId())
                .build();

        // when, then
        assertThatThrownBy(()->categoryWriteService.modifyCategoryRelation(request, mainCategoryWithSub.getMember().getId()))
                .isInstanceOf(NotValidRequestException.class);
    }

    @DisplayName("메인 카테고리를 서브 레벨로 변경할 때 연관된 메인 카테고리 아이디 값이 널이면 예외 발생")
    @Test
    void occurExceptionWhenCheckParentCategoryNull() {
        // given
        var mainCategory = mainCategories.get(3);
        var request = CategoryRelationModifyRequest.builder()
                .categoryId(mainCategory.getId())
                .level(1)
                .mainCategoryId(null)
                .build();
        // when, then
        assertThatThrownBy(()-> categoryWriteService.modifyCategoryRelation(request, mainCategory.getMember().getId()))
                .isInstanceOf(NotValidRequestException.class);
    }

    @DisplayName("메인 카테고리를 서브 레벨로 변경할 때 연관된 메인 카테고리가 유효하지 않으면 예외 발생")
    @Test
    void occurExceptionWhenModifyMainCategoryToSubWithNotValidMainCategoryId() {
        // given
        var mainCategory = mainCategories.get(3);
        var request = CategoryRelationModifyRequest.builder()
                .categoryId(mainCategory.getId())
                .level(1)
                .mainCategoryId(9999L) // 유효하지 않은 아이디
                .build();

        // when, then
        assertThatThrownBy(()->categoryWriteService.modifyCategoryRelation(request, mainCategory.getMember().getId()))
                .isInstanceOf(NotValidRequestException.class);
    }

    @DisplayName("메인 카테고리를 서브 레벨로 변경할 때 연관된 카테고리에 동일한 이름이 있으면 예외 발생")
    @Test
    void occurExceptionForMainCategoryNameConflictsWithOthersWhenModifyingToSub() {
        // given
        var mainCategory = mainCategories.get(3);
        var request = CategoryModifyRequest.builder()
                .categoryId(mainCategory.getId())
                .name(subCategories.get(0).getName())
                .color(mainCategory.getColor())
                .level(1)
                .mainCategoryId(mainCategories.get(2).getId())
                .build();

        // when, then
        assertThatThrownBy(()->categoryWriteService.modifyCategory(request, mainCategory.getMember().getId()))
                .isInstanceOf(NotValidRequestException.class);

    }

    @DisplayName("메인 카테고리를 서브 레벨로 변경 및 이름 수정시 잘 동작하는지")
    @Test
    void modifyMainCategoryToSubWell() {
        // given
        var name = "new name";
        var mainCategory = mainCategories.get(3);
        var request = CategoryModifyRequest.builder()
                .categoryId(mainCategory.getId())
                .name(name)
                .color(mainCategory.getColor())
                .level(1)
                .mainCategoryId(mainCategories.get(2).getId()) // 다른 메인 카테고리에 등록
                .build();
        // when
        categoryWriteService.modifyCategory(request, mainCategory.getMember().getId() );

        // then
        var category = categoryRepository.findById(mainCategory.getId())
                .orElseThrow();
        Assertions.assertEquals( 1, category.getLevel());
        Assertions.assertEquals(name, category.getName());
    }
}