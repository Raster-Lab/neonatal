package com.nicusystem.nutrition;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for {@link FeedingOrderController}.
 */
@SpringBootTest
@AutoConfigureMockMvc
class FeedingOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FeedingOrderService feedingOrderService;

    @Test
    @WithMockUser
    void createFeedingOrder_shouldReturnCreated() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateFeedingOrderRequest request = new CreateFeedingOrderRequest(
                patientId, FeedingType.BREAST_MILK, FeedingRoute.NASOGASTRIC,
                30.0, 3, null, null, null);
        final FeedingOrderDto dto = new FeedingOrderDto(UUID.randomUUID(), patientId,
                FeedingType.BREAST_MILK, FeedingRoute.NASOGASTRIC, 30.0, 3,
                null, null, null, null, null, null);
        when(feedingOrderService.createFeedingOrder(any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(post("/api/v1/feeding-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.feedingType").value("BREAST_MILK"))
                .andExpect(jsonPath("$.feedingRoute").value("NASOGASTRIC"));
    }

    @Test
    @WithMockUser
    void getFeedingOrder_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final FeedingOrderDto dto = new FeedingOrderDto(id, UUID.randomUUID(),
                FeedingType.FORMULA, FeedingRoute.ORAL, 20.0, 4,
                null, null, null, null, null, null);
        when(feedingOrderService.getFeedingOrderById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/feeding-orders/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.feedingType").value("FORMULA"));
    }

    @Test
    @WithMockUser
    void getFeedingOrdersByPatient_shouldReturnPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final FeedingOrderDto dto = new FeedingOrderDto(UUID.randomUUID(), patientId,
                FeedingType.BREAST_MILK, FeedingRoute.NASOGASTRIC, 30.0, 3,
                null, null, null, null, null, null);
        when(feedingOrderService.getFeedingOrdersByPatient(eq(patientId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/feeding-orders/patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].volumeMl").value(30.0));
    }

    @Test
    @WithMockUser
    void getFeedingOrdersByPatientAndType_shouldReturnFilteredPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final FeedingOrderDto dto = new FeedingOrderDto(UUID.randomUUID(), patientId,
                FeedingType.DONOR_MILK, FeedingRoute.NASOGASTRIC, 25.0, 3,
                null, null, null, null, null, null);
        when(feedingOrderService.getFeedingOrdersByPatientAndType(
                eq(patientId), eq(FeedingType.DONOR_MILK), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/feeding-orders/patient/{patientId}/type/{type}",
                        patientId, "DONOR_MILK"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].feedingType").value("DONOR_MILK"));
    }

    @Test
    @WithMockUser
    void discontinueFeedingOrder_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final FeedingOrderDto dto = new FeedingOrderDto(id, UUID.randomUUID(),
                FeedingType.BREAST_MILK, FeedingRoute.NASOGASTRIC, 30.0, 3,
                null, Instant.now(), null, null, null, null);
        when(feedingOrderService.discontinueFeedingOrder(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(patch("/api/v1/feeding-orders/{id}/discontinue", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.discontinuedAt").isNotEmpty());
    }
}
