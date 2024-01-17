package com.ddokddak.activityRecord.api;

import com.ddokddak.utils.security.WithMockCustomUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
public class ActivityRecordIntegrationTest {

    @WithMockCustomUser
    @DisplayName("활동 내역 기록이 잘 수행되는지 확인_201")
    @Test
    void createActivityRecord() throws Exception {

    }

    @WithMockCustomUser
    @DisplayName("활동 내역 기록 시도 중 잘못된 시간 데이터 전달시 예외 발생_400")
    @Test
    void createActivityRecordWithException() throws Exception {

    }
}
