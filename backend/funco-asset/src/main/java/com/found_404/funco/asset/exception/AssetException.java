package com.found_404.funco.asset.exception;

import com.found_404.funco.global.exception.BaseException;

public class AssetException extends BaseException {

	public AssetException(AssetErrorCode assetErrorCode) {
		super(assetErrorCode.getHttpStatus(), assetErrorCode.name(), assetErrorCode.getErrorMsg());
	}


}
