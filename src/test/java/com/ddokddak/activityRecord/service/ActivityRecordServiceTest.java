package com.ddokddak.activityRecord.service;

import com.ddokddak.DatabaseCleanUp;
import com.ddokddak.activityRecord.dto.ReadActivityRecordRequest;
import com.ddokddak.activityRecord.entity.ActivityRecord;
import com.ddokddak.activityRecord.fixture.ActivityRecordFixture;
import com.ddokddak.activityRecord.repository.ActivityRecordRepository;
import com.ddokddak.category.dto.CategoryAddRequest;
import com.ddokddak.category.dto.CategoryModifyRequest;
import com.ddokddak.category.dto.CategoryRelationModifyRequest;
import com.ddokddak.category.dto.CategoryValueModifyRequest;
import com.ddokddak.category.entity.Category;
import com.ddokddak.category.fixture.CategoryFixture;
import com.ddokddak.category.repository.CategoryRepository;
import com.ddokddak.category.service.CategoryWriteService;
import com.ddokddak.common.exception.NotValidRequestException;
import com.ddokddak.member.Member;
import com.ddokddak.member.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@AutoConfigureMockMvc
@SpringBootTest
//@Transactional // 롤백 수행 <- 레이지 로딩이 동작하지 않음
class ActivityRecordServiceTest {

    @Autowired
    ActivityRecordReadService activityRecordReadService;
    @Autowired
    ActivityRecordRepository activityRecordRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    DatabaseCleanUp databaseCleanUp;

    private Member member;
    private List<Category> mainCategories;
    private List<Category> subCategories;

    private List<ActivityRecord> activityRecords;

    @BeforeEach
    void setUp() {
        this.member = Member.builder().id(1L).build();
        memberRepository.save(member);

        this.mainCategories = CategoryFixture.createMainCategories(0, 4, member);
        categoryRepository.saveAll(mainCategories);

        // 전달하는 숫자 파라미터에 따라 이름이 정해짐
        this.subCategories = CategoryFixture.createSubCategories(4, 8, member, mainCategories.get(2));
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

    @WithMockUser
    @DisplayName("활동 내역 기록 조회")
    @Test
    void readActivityRecord() throws Exception {
        var req = ReadActivityRecordRequest.builder()
                .memberId(1L)
                .fromStartedAt( LocalDateTime.now().minusMonths(1) )
                .toStartedAt(LocalDateTime.now())
                .build();
        var test = activityRecordRepository.findAll();
        var result = activityRecordReadService.findByMemberIdAndStartedAtBetween( req.memberId(), req.fromStartedAt(), req.toStartedAt() );

        assertThat(result);
    }

}