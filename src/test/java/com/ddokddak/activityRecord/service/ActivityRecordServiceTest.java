package com.ddokddak.activityRecord.service;

import com.ddokddak.activityRecord.domain.dto.ReadActivityRecordRequest;
import com.ddokddak.category.domain.entity.CategoryIcon;
import com.ddokddak.category.repository.CategoryIconRepository;
import com.ddokddak.utils.DatabaseCleanUp;
import com.ddokddak.activityRecord.domain.dto.ModifyActivityRecordRequest;
import com.ddokddak.activityRecord.domain.entity.ActivityRecord;
import com.ddokddak.activityRecord.fixture.ActivityRecordFixture;
import com.ddokddak.activityRecord.repository.ActivityRecordRepository;
import com.ddokddak.category.domain.entity.Category;
import com.ddokddak.category.fixture.CategoryFixture;
import com.ddokddak.category.repository.CategoryRepository;
import com.ddokddak.member.domain.entity.Member;
import com.ddokddak.member.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest
//@Transactional // 롤백 수행 <- 레이지 로딩이 동작하지 않음
class ActivityRecordServiceTest {

    @Autowired
    ActivityRecordReadService activityRecordReadService;
    @Autowired
    ActivityRecordWriteService activityRecordWriteService;
    @Autowired
    ActivityRecordRepository activityRecordRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryIconRepository categoryIconRepository;
    @Autowired
    DatabaseCleanUp databaseCleanUp;

    private Member member;
    private CategoryIcon iconFile;
    private List<Category> mainCategories;
    private List<Category> subCategories;

    private List<ActivityRecord> activityRecords;

    @BeforeEach
    void setUp() {
        this.member = Member.builder().build();
        memberRepository.save(member);
        this.iconFile = CategoryIcon.builder()
                .filename("icon.jpg")
                .path("/")
                .originalFilename("1234.jpg")
                .build();
        categoryIconRepository.save(iconFile);

        this.mainCategories = CategoryFixture.createMainCategories(0, 4, member, iconFile);
        categoryRepository.saveAll(mainCategories);

        // 전달하는 숫자 파라미터에 따라 이름이 정해짐
        this.subCategories = CategoryFixture.createSubCategories(4, 8, member, mainCategories.get(2), iconFile);
        categoryRepository.saveAll(subCategories);

        this.activityRecords = ActivityRecordFixture.createActivityRecords( 0,4, member, mainCategories );
        activityRecordRepository.saveAll( activityRecords );

    }

    @AfterEach
    void tearDown() {
        databaseCleanUp.afterPropertiesSet();
        databaseCleanUp.execute();
        // 레포지토리를 이용해 데이터를 삭제하고자 하는 경우에는 소프트 딜리트를 사용하므로 따로 테스트용 딜리트 메소드가 필요하다.
    }

    @Test
    void updateActivityRecord() throws Exception {
        // given
        var changedContent = "AFTER CHANGE !";
        var targetId = activityRecords.get(0).getId();
        var targetCategory = activityRecords.get(0).getCategory();
        ModifyActivityRecordRequest req = ModifyActivityRecordRequest.builder()
                                                                    .id( targetId )
                                                                    .categoryId(targetCategory.getId())
                                                                    .content(changedContent)
                                                                    .startedAt(LocalDateTime.now().minusDays(1))
                                                                    .finishedAt(LocalDateTime.now().minusDays(1).plusHours(2))
                                                                    .build();

        // when
        activityRecordWriteService.modifyActivityRecord( member.getId(), req );
        var byId = activityRecordRepository.findById(targetId).orElse(null);

        // then
        Assertions.assertEquals(changedContent, Objects.nonNull(byId) ? byId.getContent() : "NULL");
    }

    @Test
    void deleteActivityRecord() throws Exception  {
        // given
        var targetId = activityRecords.get(0).getId();

        // when
        activityRecordWriteService.removeActivityRecordByMemberIdAndId(member.getId(), targetId);

        // then
        var undeleted = activityRecordRepository.findByIdAndIsDeletedFalse(targetId);

        Assertions.assertEquals(false, undeleted.isPresent());
    }

    @WithMockUser
    @DisplayName("활동 내역 기록 조회")
    @Test
    void readActivityRecord() throws Exception {
        var memberId = member.getId();
        var req = ReadActivityRecordRequest.builder()
                .fromStartedAt( LocalDateTime.now().minusMonths(1) )
                .toStartedAt(LocalDateTime.now())
                .build();

        var result = activityRecordReadService.findByMemberIdAndStartedAtBetween( memberId, req.fromStartedAt(), req.toStartedAt() );

        assertThat(result);
    }
}