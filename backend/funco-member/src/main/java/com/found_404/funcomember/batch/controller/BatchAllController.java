package com.found_404.funcomember.batch.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.found_404.funcomember.batch.dto.RankMemberInfo;
import com.found_404.funcomember.batch.service.BatchAllService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/batch")
public class BatchAllController {
	private final BatchAllService batchAllService;

	@GetMapping("/members")
	public ResponseEntity<List<RankMemberInfo>> getAllMemberList() {
		return ResponseEntity.ok(batchAllService.readAllMemberList());
	}
}
