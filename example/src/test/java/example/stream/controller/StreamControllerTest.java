package example.stream.controller;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.hao.stream.client.CreateStreamResponse;
import example.hao.stream.client.StreamClient;
import example.hao.stream.mapper.StreamMapper;
import example.hao.stream.model.Stream;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StreamControllerTest {
    @MockBean
    private StreamClient client;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private StreamMapper streamMapper;

    private final String prefix = "/v1/streams";
    private final ObjectMapper json = new ObjectMapper();
    private final String streamId = "1";

    @Test
    @Order(1)
    public void testSync() throws Exception {
        CreateStreamResponse r = new CreateStreamResponse();
        r.setId(streamId);
        when(client.add(argThat(createStreamRequest -> Integer.valueOf(streamId).equals(Integer.valueOf(createStreamRequest.getUrl().substring(createStreamRequest.getUrl().lastIndexOf("-") + 1))))))
                .thenReturn(r);
        mockMvc.perform(post(prefix + "/sync").queryParam("to", streamId)).andDo(print()).andExpect(status().isOk());
        verify(client, times(1)).add(argThat(createStreamRequest -> Integer.valueOf(streamId).equals(Integer.valueOf(createStreamRequest.getUrl().substring(createStreamRequest.getUrl().lastIndexOf("-") + 1)))));
    }

    @Test
    @Order(2)
    public void testList() throws Exception {
        MvcResult r = mockMvc.perform(get(prefix)).andDo(print()).andExpect(status().isOk()).andReturn();
        List<Stream> ss = json.readValue(r.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
        Assertions.assertEquals(1, ss.size());
        Assertions.assertEquals(streamId, ss.get(0).getStreamId());
    }

    @Test
    @Order(3)
    public void testDeleteAll() throws Exception {
        mockMvc.perform(delete(prefix)).andDo(print()).andExpect(status().isOk());
        Assertions.assertEquals(0, streamMapper.selectList(new QueryWrapper<>()).size());
    }
}
