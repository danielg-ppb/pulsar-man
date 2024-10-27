package com.danielg.pulsar_man.service.impl;

import com.danielg.pulsar_man.dto.TopicListDto;
import com.danielg.pulsar_man.service.PulsarAdminService;
import com.danielg.pulsar_man.state.InMemoryPulsarAdminState;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PulsarAdminServiceImpl implements PulsarAdminService {
    private InMemoryPulsarAdminState pulsarAdminState;

    public PulsarAdminServiceImpl(InMemoryPulsarAdminState pulsarAdminState) {
        this.pulsarAdminState = pulsarAdminState;
    }

    public void initializePulsarAdmin(String serviceUrl) throws PulsarClientException {
        if (pulsarAdminState != null && pulsarAdminState.getPulsarAdmin() != null) {
            pulsarAdminState.getPulsarAdmin().close();
        }

        if (pulsarAdminState != null) {
            pulsarAdminState.initializePulsarAdmin(serviceUrl);
        }
    }

    @Override
    public TopicListDto listTopics(String tenant, String namespace) {
        try {
            String namespacePath = tenant + "/" + namespace;
            //            return pulsarAdminState.getPulsarAdmin().topics().getList(namespacePath); -> with partitions

            return new TopicListDto(pulsarAdminState.getPulsarAdmin().topics().getPartitionedTopicList(namespacePath));
        } catch (Exception e) {
            throw new RuntimeException("Failed to list topics", e);
        }
    }
}
