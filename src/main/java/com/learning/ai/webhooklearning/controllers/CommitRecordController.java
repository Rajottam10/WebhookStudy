package com.learning.ai.webhooklearning.controllers;

import com.learning.ai.webhooklearning.services.CommitRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/webhooks")
public class CommitRecordController {
    private final CommitRecordService commitRecordService;

    public CommitRecordController(CommitRecordService commitRecordService){
        this.commitRecordService = commitRecordService;
    }

    @PostMapping("/github")
    public ResponseEntity<Void> receive(
            @RequestHeader("X-GitHub-Event") String event,
            @RequestHeader(value = "X-GitHub-Delivery", required = false) String deliveryId,
            @RequestBody String payload) {

        log.info("Webhook received: event={}, delivery={}", event, deliveryId);

        if ("push".equals(event)) {
            commitRecordService.saveCommits(payload);
        }
        return ResponseEntity.ok().build();
    }
}
