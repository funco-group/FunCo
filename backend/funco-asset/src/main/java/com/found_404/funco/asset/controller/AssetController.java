package com.found_404.funco.asset.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.found_404.funco.asset.service.AssetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/asset")
@RequiredArgsConstructor
public class AssetController {
	private final AssetService assetService;
	
}
