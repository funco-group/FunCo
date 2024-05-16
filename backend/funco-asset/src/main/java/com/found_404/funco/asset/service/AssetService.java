package com.found_404.funco.asset.service;

import org.springframework.stereotype.Service;

import com.found_404.funco.asset.domain.repository.AssetRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssetService {
	private final AssetRepository assetRepository;
}
