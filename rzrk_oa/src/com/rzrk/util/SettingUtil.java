/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.core.io.ClassPathResource;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;
import com.rzrk.bean.Setting;
import com.rzrk.bean.Setting.CommentAuthority;
import com.rzrk.bean.Setting.CommentDisplayType;
import com.rzrk.bean.Setting.CurrencyType;
import com.rzrk.bean.Setting.InstantMessagingPosition;
import com.rzrk.bean.Setting.LeaveMessageDisplayType;
import com.rzrk.bean.Setting.Operator;
import com.rzrk.bean.Setting.RoundType;
import com.rzrk.bean.Setting.ScoreType;
import com.rzrk.bean.Setting.StoreFreezeTime;
import com.rzrk.bean.Setting.WatermarkPosition;

/**
 * 工具类 - 系统配置
 */

public class SettingUtil {

	private static final String CACHE_MANAGER_BEAN_NAME = "cacheManager";// cacheManager
																			// Bean名称
	private static final String UNICORN_XML_FILE_NAME = "rzrk.xml";// rzrk.xml配置文件名称
	private static final String SETTING_CACHE_KEY = "UNICORN_SETTING";// Setting缓存Key

	/**
	 * 读取系统配置信息
	 * 
	 * @return Setting
	 * 
	 * @throws URISyntaxException
	 * 
	 * @throws DocumentException
	 * 
	 * @throws IOException
	 */
	public static Setting readSetting() throws URISyntaxException,
			DocumentException, IOException {
		File rzrkXmlFile = new ClassPathResource(UNICORN_XML_FILE_NAME)
				.getFile();
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(rzrkXmlFile);
		Node systemNameNode = document
				.selectSingleNode("/rzrk/setting/systemName");
		Node systemVersionNode = document
				.selectSingleNode("/rzrk/setting/systemVersion");
		Node systemDescriptionNode = document
				.selectSingleNode("/rzrk/setting/systemDescription");
		Node contextPathNode = document
				.selectSingleNode("/rzrk/setting/contextPath");
		Node imageUploadPathNode = document
				.selectSingleNode("/rzrk/setting/imageUploadPath");
		Node imageBrowsePathNode = document
				.selectSingleNode("/rzrk/setting/imageBrowsePath");
		Node fileUploadPathNode = document
				.selectSingleNode("/rzrk/setting/fileUploadPath");
		Node fileBrowsePathNode = document
				.selectSingleNode("/rzrk/setting/fileBrowsePath");
		Node adminLoginUrlNode = document
				.selectSingleNode("/rzrk/setting/adminLoginUrl");
		Node adminLoginProcessingUrlNode = document
				.selectSingleNode("/rzrk/setting/adminLoginProcessingUrl");
		Node isShowPoweredInfoNode = document
				.selectSingleNode("/rzrk/setting/isShowPoweredInfo");
		Node shopNameNode = document
				.selectSingleNode("/rzrk/setting/rzrkName");
		Node shopUrlNode = document
				.selectSingleNode("/rzrk/setting/rzrkUrl");
		Node shopLogoPathNode = document
				.selectSingleNode("/rzrk/setting/rzrkLogoPath");
		Node hotSearchNode = document
				.selectSingleNode("/rzrk/setting/hotSearch");
		Node metaKeywordsNode = document
				.selectSingleNode("/rzrk/setting/metaKeywords");
		Node metaDescriptionNode = document
				.selectSingleNode("/rzrk/setting/metaDescription");
		/*
		Node addressNode = document
				.selectSingleNode("/rzrk/setting/address");
		Node phoneNode = document.selectSingleNode("/rzrk/setting/phone");
		Node zipCodeNode = document
				.selectSingleNode("/rzrk/setting/zipCode");
		Node emailNode = document.selectSingleNode("/rzrk/setting/email");
		*/
		Node certtextNode = document
				.selectSingleNode("/rzrk/setting/certtext");
		Node currencyTypeNode = document
				.selectSingleNode("/rzrk/setting/currencyType");
		Node currencySignNode = document
				.selectSingleNode("/rzrk/setting/currencySign");
		Node currencyUnitNode = document
				.selectSingleNode("/rzrk/setting/currencyUnit");
		Node priceScaleNode = document
				.selectSingleNode("/rzrk/setting/priceScale");
		Node priceRoundTypeNode = document
				.selectSingleNode("/rzrk/setting/priceRoundType");
		Node storeAlertCountNode = document
				.selectSingleNode("/rzrk/setting/storeAlertCount");
		Node storeFreezeTimeNode = document
				.selectSingleNode("/rzrk/setting/storeFreezeTime");
		Node isLoginFailureLockNode = document
				.selectSingleNode("/rzrk/setting/isLoginFailureLock");
		Node loginFailureLockCountNode = document
				.selectSingleNode("/rzrk/setting/loginFailureLockCount");
		Node loginFailureLockTimeNode = document
				.selectSingleNode("/rzrk/setting/loginFailureLockTime");
		Node isRegisterEnabledNode = document
				.selectSingleNode("/rzrk/setting/isRegisterEnabled");
		Node watermarkImagePathNode = document
				.selectSingleNode("/rzrk/setting/watermarkImagePath");
		Node watermarkPositionNode = document
				.selectSingleNode("/rzrk/setting/watermarkPosition");
		Node watermarkAlphaNode = document
				.selectSingleNode("/rzrk/setting/watermarkAlpha");
		Node bigGoodsImageWidthNode = document
				.selectSingleNode("/rzrk/setting/bigGoodsImageWidth");
		Node bigGoodsImageHeightNode = document
				.selectSingleNode("/rzrk/setting/bigGoodsImageHeight");
		Node smallGoodsImageWidthNode = document
				.selectSingleNode("/rzrk/setting/smallGoodsImageWidth");
		Node smallGoodsImageHeightNode = document
				.selectSingleNode("/rzrk/setting/smallGoodsImageHeight");
		Node thumbnailGoodsImageWidthNode = document
				.selectSingleNode("/rzrk/setting/thumbnailGoodsImageWidth");
		Node thumbnailGoodsImageHeightNode = document
				.selectSingleNode("/rzrk/setting/thumbnailGoodsImageHeight");
		Node defaultBigGoodsImagePathNode = document
				.selectSingleNode("/rzrk/setting/defaultBigGoodsImagePath");
		Node defaultSmallGoodsImagePathNode = document
				.selectSingleNode("/rzrk/setting/defaultSmallGoodsImagePath");
		Node defaultThumbnailGoodsImagePathNode = document
				.selectSingleNode("/rzrk/setting/defaultThumbnailGoodsImagePath");
		Node isShowMarketPriceNode = document
				.selectSingleNode("/rzrk/setting/isShowMarketPrice");
		Node isShowInventoryNode = document
				.selectSingleNode("/rzrk/setting/isShowInventory");
		Node defaultMarketPriceOperatorNode = document
				.selectSingleNode("/rzrk/setting/defaultMarketPriceOperator");
		Node defaultMarketPriceNumberNode = document
				.selectSingleNode("/rzrk/setting/defaultMarketPriceNumber");
		Node smtpFromMailNode = document
				.selectSingleNode("/rzrk/setting/smtpFromMail");
		Node smtpHostNode = document
				.selectSingleNode("/rzrk/setting/smtpHost");
		Node smtpPortNode = document
				.selectSingleNode("/rzrk/setting/smtpPort");
		Node smtpUsernameNode = document
				.selectSingleNode("/rzrk/setting/smtpUsername");
		Node smtpPasswordNode = document
				.selectSingleNode("/rzrk/setting/smtpPassword");
		Node scoreTypeNode = document
				.selectSingleNode("/rzrk/setting/scoreType");
		Node scoreScaleNode = document
				.selectSingleNode("/rzrk/setting/scoreScale");
		Node isGzipEnabledNode = document
				.selectSingleNode("/rzrk/setting/isGzipEnabled");
		Node buildHtmlDelayTimeNode = document
				.selectSingleNode("/rzrk/setting/buildHtmlDelayTime");
		Node isInstantMessagingEnabledNode = document
				.selectSingleNode("/rzrk/setting/isInstantMessagingEnabled");
		Node instantMessagingPositionNode = document
				.selectSingleNode("/rzrk/setting/instantMessagingPosition");
		Node instantMessagingTitleNode = document
				.selectSingleNode("/rzrk/setting/instantMessagingTitle");
		Node isLeaveMessageEnabledNode = document
				.selectSingleNode("/rzrk/setting/isLeaveMessageEnabled");
		Node isLeaveMessageCaptchaEnabledNode = document
				.selectSingleNode("/rzrk/setting/isLeaveMessageCaptchaEnabled");
		Node leaveMessageDisplayTypeNode = document
				.selectSingleNode("/rzrk/setting/leaveMessageDisplayType");
		Node isCommentEnabledNode = document
				.selectSingleNode("/rzrk/setting/isCommentEnabled");
		Node isCommentCaptchaEnabledNode = document
				.selectSingleNode("/rzrk/setting/isCommentCaptchaEnabled");
		Node commentAuthorityNode = document
				.selectSingleNode("/rzrk/setting/commentAuthority");
		Node commentDisplayTypeNode = document
				.selectSingleNode("/rzrk/setting/commentDisplayType");
        // add by ypu 2012.5.19
        Node scoreGoodsRatioNode = document.selectSingleNode("/rzrk/setting/scoreGoodsRatio");
        Node scoreRegisterNode = document.selectSingleNode("/rzrk/setting/scoreRegister");
        Node scoreLoginNode = document.selectSingleNode("/rzrk/setting/scoreLogin");
        Node scoreEmailNode = document.selectSingleNode("/rzrk/setting/scoreEmail");
        Node scoreMobileNode = document.selectSingleNode("/rzrk/setting/scoreMobile");
        Node scoreFullInformNode = document.selectSingleNode("/rzrk/setting/scoreFullInform");
        Node scoreCommentNode = document.selectSingleNode("/rzrk/setting/scoreComment");
        Node scoreMarkNode = document.selectSingleNode("/rzrk/setting/scoreMark");

        Node smsEnabledNode = document.selectSingleNode("/rzrk/setting/smsEnabled");
        Node smsHostNode = document.selectSingleNode("/rzrk/setting/smsHost");
        Node smsUsernameNode = document.selectSingleNode("/rzrk/setting/smsUsername");
        Node smsPasswordNode = document.selectSingleNode("/rzrk/setting/smsPassword");
        Node modelAddUserUrlNode = document.selectSingleNode("/rzrk/setting/modelAddUserUrl");
        Node modelUserUrlNode = document.selectSingleNode("/rzrk/setting/modelUserUrl");
        Node modelOnlineUserUrlNode = document.selectSingleNode("/rzrk/setting/modelOnlineUserUrl");
		Setting setting = new Setting();

        setting.setScoreGoodsRatio(Float.valueOf(scoreGoodsRatioNode.getText()));
        setting.setScoreRegister(Integer.valueOf(scoreRegisterNode.getText()));
        setting.setScoreEmail(Integer.valueOf(scoreEmailNode.getText()));
        setting.setScoreMobile(Integer.valueOf(scoreMobileNode.getText()));
        setting.setScoreFullInform(Integer.valueOf(scoreFullInformNode.getText()));
        setting.setScoreLogin(Integer.valueOf(scoreLoginNode.getText()));
        setting.setScoreComment(Integer.parseInt(scoreCommentNode.getText()));
        setting.setScoreMark(Integer.parseInt(scoreMarkNode.getText()));

        setting.setSmsEnabled(Boolean.valueOf(smsEnabledNode.getText()));
        setting.setSmsHost(smsHostNode.getText());
        setting.setSmsUsername(smsUsernameNode.getText());
        setting.setSmsPassword(smsPasswordNode.getText());

		setting.setSystemName(systemNameNode.getText());
		setting.setSystemVersion(systemVersionNode.getText());
		setting.setSystemDescription(systemDescriptionNode.getText());
		setting.setContextPath(contextPathNode.getText());
		setting.setImageUploadPath(imageUploadPathNode.getText());
		setting.setImageBrowsePath(imageBrowsePathNode.getText());
		setting.setFileUploadPath(fileUploadPathNode.getText());
		setting.setFileBrowsePath(fileBrowsePathNode.getText());
		setting.setAdminLoginUrl(adminLoginUrlNode.getText());
		setting.setAdminLoginProcessingUrl(adminLoginProcessingUrlNode
				.getText());
		setting.setIsShowPoweredInfo(Boolean.valueOf(isShowPoweredInfoNode
				.getText()));
		setting.setShopName(shopNameNode.getText());
		setting.setShopUrl(shopUrlNode.getText());
		setting.setShopLogoPath(shopLogoPathNode.getText());
		setting.setHotSearch(hotSearchNode.getText());
		setting.setMetaKeywords(metaKeywordsNode.getText());
		setting.setMetaDescription(metaDescriptionNode.getText());
		/*
		setting.setAddress(addressNode.getText());
		setting.setPhone(phoneNode.getText());
		setting.setZipCode(zipCodeNode.getText());
		setting.setEmail(emailNode.getText());
		*/
		setting.setCerttext(certtextNode.getText());
		setting.setCurrencyType(CurrencyType.valueOf(currencyTypeNode.getText()));
		setting.setCurrencySign(currencySignNode.getText());
		setting.setCurrencyUnit(currencyUnitNode.getText());
		setting.setPriceScale(Integer.valueOf(priceScaleNode.getText()));
		setting.setPriceRoundType(RoundType.valueOf(priceRoundTypeNode
				.getText()));
		setting.setStoreAlertCount(Integer.valueOf(storeAlertCountNode
				.getText()));
		setting.setStoreFreezeTime(StoreFreezeTime.valueOf(storeFreezeTimeNode
				.getText()));
		setting.setIsLoginFailureLock(Boolean.valueOf(isLoginFailureLockNode
				.getText()));
		setting.setLoginFailureLockCount(Integer
				.valueOf(loginFailureLockCountNode.getText()));
		setting.setLoginFailureLockTime(Integer
				.valueOf(loginFailureLockTimeNode.getText()));
		setting.setIsRegisterEnabled(Boolean.valueOf(isRegisterEnabledNode
				.getText()));
		setting.setWatermarkImagePath(watermarkImagePathNode.getText());
		setting.setWatermarkPosition(WatermarkPosition
				.valueOf(watermarkPositionNode.getText()));
		setting.setWatermarkAlpha(Integer.valueOf(watermarkAlphaNode.getText()));
		setting.setBigGoodsImageWidth(Integer.valueOf(bigGoodsImageWidthNode
				.getText()));
		setting.setBigGoodsImageHeight(Integer.valueOf(bigGoodsImageHeightNode
				.getText()));
		setting.setSmallGoodsImageWidth(Integer
				.valueOf(smallGoodsImageWidthNode.getText()));
		setting.setSmallGoodsImageHeight(Integer
				.valueOf(smallGoodsImageHeightNode.getText()));
		setting.setThumbnailGoodsImageWidth(Integer
				.valueOf(thumbnailGoodsImageWidthNode.getText()));
		setting.setThumbnailGoodsImageHeight(Integer
				.valueOf(thumbnailGoodsImageHeightNode.getText()));
		setting.setDefaultBigGoodsImagePath(defaultBigGoodsImagePathNode
				.getText());
		setting.setDefaultSmallGoodsImagePath(defaultSmallGoodsImagePathNode
				.getText());
		setting.setDefaultThumbnailGoodsImagePath(defaultThumbnailGoodsImagePathNode
				.getText());
		setting.setIsShowMarketPrice(Boolean.valueOf(isShowMarketPriceNode
				.getText()));
		setting.setIsShowInventory(Boolean.valueOf(isShowInventoryNode
				.getText()));
		setting.setDefaultMarketPriceOperator(Operator
				.valueOf(defaultMarketPriceOperatorNode.getText()));
		setting.setDefaultMarketPriceNumber(BigDecimal.valueOf(Double
				.valueOf(defaultMarketPriceNumberNode.getText())));
		setting.setSmtpFromMail(smtpFromMailNode.getText());
		setting.setSmtpHost(smtpHostNode.getText());
		setting.setSmtpPort(Integer.valueOf(smtpPortNode.getText()));
		setting.setSmtpUsername(smtpUsernameNode.getText());
		setting.setSmtpPassword(smtpPasswordNode.getText());
		setting.setScoreType(ScoreType.valueOf(scoreTypeNode.getText()));
		setting.setScoreScale(Double.valueOf(scoreScaleNode.getText()));
		setting.setBuildHtmlDelayTime(Integer.valueOf(buildHtmlDelayTimeNode
				.getText()));
		setting.setIsGzipEnabled(Boolean.valueOf(isGzipEnabledNode.getText()));
		setting.setIsInstantMessagingEnabled(Boolean
				.valueOf(isInstantMessagingEnabledNode.getText()));
		setting.setInstantMessagingPosition(InstantMessagingPosition
				.valueOf(instantMessagingPositionNode.getText()));
		setting.setInstantMessagingTitle(instantMessagingTitleNode.getText());
		setting.setIsLeaveMessageEnabled(Boolean
				.valueOf(isLeaveMessageEnabledNode.getText()));
		setting.setIsLeaveMessageCaptchaEnabled(Boolean
				.valueOf(isLeaveMessageCaptchaEnabledNode.getText()));
		setting.setLeaveMessageDisplayType(LeaveMessageDisplayType
				.valueOf(leaveMessageDisplayTypeNode.getText()));
		setting.setIsCommentEnabled(Boolean.valueOf(isCommentEnabledNode
				.getText()));
		setting.setIsCommentCaptchaEnabled(Boolean
				.valueOf(isCommentCaptchaEnabledNode.getText()));
		setting.setCommentAuthority(CommentAuthority
				.valueOf(commentAuthorityNode.getText()));
		setting.setCommentDisplayType(CommentDisplayType
				.valueOf(commentDisplayTypeNode.getText()));
		setting.setModelAddUserUrl(modelAddUserUrlNode.getText());
		setting.setModelUserUrl(modelUserUrlNode.getText());
		setting.setModelOnlineUserUrl(modelOnlineUserUrlNode.getText());
		return setting;
	}

	/**
	 * 获取系统配置信息
	 * 
	 * @return Setting
	 */
	public static Setting getSetting() {
		Setting setting = null;
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil
				.getBean(CACHE_MANAGER_BEAN_NAME);
		try {
			setting = (Setting) generalCacheAdministrator
					.getFromCache(SETTING_CACHE_KEY);
		} catch (NeedsRefreshException needsRefreshException) {
			boolean updateSucceeded = false;
			try {
				setting = readSetting();
				generalCacheAdministrator
						.putInCache(SETTING_CACHE_KEY, setting);
				updateSucceeded = true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (!updateSucceeded) {
					generalCacheAdministrator.cancelUpdate(SETTING_CACHE_KEY);
				}
			}
		}
		return setting;
	}

	/**
	 * 写入系统配置信息
	 * 
	 * @return Setting
	 */
	public static void writeSetting(Setting setting) {
		File rzrkXmlFile = null;
		Document document = null;
		try {
			rzrkXmlFile = new ClassPathResource(UNICORN_XML_FILE_NAME)
					.getFile();
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(rzrkXmlFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element rootElement = document.getRootElement();
		Element settingElement = rootElement.element("setting");
		Node shopNameNode = document
				.selectSingleNode("/rzrk/setting/rzrkName");
		Node shopUrlNode = document
				.selectSingleNode("/rzrk/setting/rzrkUrl");
		Node shopLogoPathNode = document
				.selectSingleNode("/rzrk/setting/rzrkLogoPath");
		Node hotSearchNode = document
				.selectSingleNode("/rzrk/setting/hotSearch");
		Node metaKeywordsNode = document
				.selectSingleNode("/rzrk/setting/metaKeywords");
		Node metaDescriptionNode = document
				.selectSingleNode("/rzrk/setting/metaDescription");
		/*
		Node addressNode = document
				.selectSingleNode("/rzrk/setting/address");
		Node phoneNode = document.selectSingleNode("/rzrk/setting/phone");
		Node zipCodeNode = document
				.selectSingleNode("/rzrk/setting/zipCode");
		Node emailNode = document.selectSingleNode("/rzrk/setting/email");
		*/
		Node certtextNode = document
				.selectSingleNode("/rzrk/setting/certtext");
		Node currencyTypeNode = document
				.selectSingleNode("/rzrk/setting/currencyType");
		Node currencySignNode = document
				.selectSingleNode("/rzrk/setting/currencySign");
		Node currencyUnitNode = document
				.selectSingleNode("/rzrk/setting/currencyUnit");
		Node priceScaleNode = document
				.selectSingleNode("/rzrk/setting/priceScale");
		Node priceRoundTypeNode = document
				.selectSingleNode("/rzrk/setting/priceRoundType");
		Node storeAlertCountNode = document
				.selectSingleNode("/rzrk/setting/storeAlertCount");
		Node storeFreezeTimeNode = document
				.selectSingleNode("/rzrk/setting/storeFreezeTime");
		Node isLoginFailureLockNode = document
				.selectSingleNode("/rzrk/setting/isLoginFailureLock");
		Node loginFailureLockCountNode = document
				.selectSingleNode("/rzrk/setting/loginFailureLockCount");
		Node loginFailureLockTimeNode = document
				.selectSingleNode("/rzrk/setting/loginFailureLockTime");
		Node isRegisterEnabledNode = document
				.selectSingleNode("/rzrk/setting/isRegisterEnabled");
		Node watermarkImagePathNode = document
				.selectSingleNode("/rzrk/setting/watermarkImagePath");
		Node watermarkPositionNode = document
				.selectSingleNode("/rzrk/setting/watermarkPosition");
		Node watermarkAlphaNode = document
				.selectSingleNode("/rzrk/setting/watermarkAlpha");
		Node bigGoodsImageWidthNode = document
				.selectSingleNode("/rzrk/setting/bigGoodsImageWidth");
		Node bigGoodsImageHeightNode = document
				.selectSingleNode("/rzrk/setting/bigGoodsImageHeight");
		Node smallGoodsImageWidthNode = document
				.selectSingleNode("/rzrk/setting/smallGoodsImageWidth");
		Node smallGoodsImageHeightNode = document
				.selectSingleNode("/rzrk/setting/smallGoodsImageHeight");
		Node thumbnailGoodsImageWidthNode = document
				.selectSingleNode("/rzrk/setting/thumbnailGoodsImageWidth");
		Node thumbnailGoodsImageHeightNode = document
				.selectSingleNode("/rzrk/setting/thumbnailGoodsImageHeight");
		Node defaultBigGoodsImagePathNode = document
				.selectSingleNode("/rzrk/setting/defaultBigGoodsImagePath");
		Node defaultSmallGoodsImagePathNode = document
				.selectSingleNode("/rzrk/setting/defaultSmallGoodsImagePath");
		Node defaultThumbnailGoodsImagePathNode = document
				.selectSingleNode("/rzrk/setting/defaultThumbnailGoodsImagePath");
		Node isShowMarketPriceNode = document
				.selectSingleNode("/rzrk/setting/isShowMarketPrice");
		Node isShowInventoryNode = document
				.selectSingleNode("/rzrk/setting/isShowInventory");
		Node defaultMarketPriceOperatorNode = document
				.selectSingleNode("/rzrk/setting/defaultMarketPriceOperator");
		Node defaultMarketPriceNumberNode = document
				.selectSingleNode("/rzrk/setting/defaultMarketPriceNumber");
		Node smtpFromMailNode = document
				.selectSingleNode("/rzrk/setting/smtpFromMail");
		Node smtpHostNode = document
				.selectSingleNode("/rzrk/setting/smtpHost");
		Node smtpPortNode = document
				.selectSingleNode("/rzrk/setting/smtpPort");
		Node smtpUsernameNode = document
				.selectSingleNode("/rzrk/setting/smtpUsername");
		Node smtpPasswordNode = document
				.selectSingleNode("/rzrk/setting/smtpPassword");
		Node scoreTypeNode = document
				.selectSingleNode("/rzrk/setting/scoreType");
		Node scoreScaleNode = document
				.selectSingleNode("/rzrk/setting/scoreScale");
		Node buildHtmlDelayTimeNode = document
				.selectSingleNode("/rzrk/setting/buildHtmlDelayTime");
		Node isGzipEnabledNode = document
				.selectSingleNode("/rzrk/setting/isGzipEnabled");
		Node isInstantMessagingEnabledNode = document
				.selectSingleNode("/rzrk/setting/isInstantMessagingEnabled");
		Node instantMessagingPositionNode = document
				.selectSingleNode("/rzrk/setting/instantMessagingPosition");
		Node instantMessagingTitleNode = document
				.selectSingleNode("/rzrk/setting/instantMessagingTitle");
		Node isLeaveMessageEnabledNode = document
				.selectSingleNode("/rzrk/setting/isLeaveMessageEnabled");
		Node isLeaveMessageCaptchaEnabledNode = document
				.selectSingleNode("/rzrk/setting/isLeaveMessageCaptchaEnabled");
		Node leaveMessageDisplayTypeNode = document
				.selectSingleNode("/rzrk/setting/leaveMessageDisplayType");
		Node isCommentEnabledNode = document
				.selectSingleNode("/rzrk/setting/isCommentEnabled");
		Node isCommentCaptchaEnabledNode = document
				.selectSingleNode("/rzrk/setting/isCommentCaptchaEnabled");
		Node commentAuthorityNode = document
				.selectSingleNode("/rzrk/setting/commentAuthority");
		Node commentDisplayTypeNode = document
				.selectSingleNode("/rzrk/setting/commentDisplayType");

        Node scoreGoodsRatioNode = document.selectSingleNode("/rzrk/setting/scoreGoodsRatio");
        Node scoreRegisterNode = document.selectSingleNode("/rzrk/setting/scoreRegister");
        Node scoreLoginNode = document.selectSingleNode("/rzrk/setting/scoreLogin");
        Node scoreEmailNode = document.selectSingleNode("/rzrk/setting/scoreEmail");
        Node scoreMobileNode = document.selectSingleNode("/rzrk/setting/scoreMobile");
        Node scoreFullInformNode = document.selectSingleNode("/rzrk/setting/scoreFullInform");
        Node scoreCommentNode = document.selectSingleNode("/rzrk/setting/scoreComment");
        Node scoreMarkNode = document.selectSingleNode("/rzrk/setting/scoreMark");
        Node smsEnabledNode = document.selectSingleNode("/rzrk/setting/smsEnabled");
        Node smsHostNode = document.selectSingleNode("/rzrk/setting/smsHost");
        Node smsUsernameNode = document.selectSingleNode("/rzrk/setting/smsUsername");
        Node smsPasswordNode = document.selectSingleNode("/rzrk/setting/smsPassword");

        if(smsEnabledNode == null){
            smsHostNode = settingElement.addElement("smsEnabled");
        }

        if(smsHostNode == null){
            smsHostNode = settingElement.addElement("smsHost");
        }

        if(smsUsernameNode == null){
            smsUsernameNode = settingElement.addElement("smsUsername");
        }

        if(smsPasswordNode == null){
            smsPasswordNode = settingElement.addElement("smsPassword");
        }

        if(scoreGoodsRatioNode == null){
            scoreGoodsRatioNode = settingElement.addElement("scoreGoodsRatio");
        }

        if(scoreRegisterNode == null){
            scoreRegisterNode = settingElement.addElement("scoreRegister");
        }

        if(scoreLoginNode == null){
            scoreLoginNode = settingElement.addElement("scoreLogin");
        }

        if(scoreEmailNode == null){
            scoreEmailNode = settingElement.addElement("scoreEmail");
        }

        if(scoreMobileNode == null){
            scoreMobileNode = settingElement.addElement("scoreMobile");
        }

        if(scoreFullInformNode == null){
            scoreFullInformNode = settingElement.addElement("scoreFullInform");
        }

        if(scoreCommentNode == null){
            scoreCommentNode = settingElement.addElement("scoreComment");
        }

        if(scoreMarkNode == null){
            scoreMarkNode = settingElement.addElement("scoreMark");
        }

		if (shopNameNode == null) {
			shopNameNode = settingElement.addElement("shopName");
		}
		if (shopUrlNode == null) {
			shopUrlNode = settingElement.addElement("shopUrl");
		}
		if (shopLogoPathNode == null) {
			shopLogoPathNode = settingElement.addElement("shopLogoPath");
		}
		if (hotSearchNode == null) {
			hotSearchNode = settingElement.addElement("hotSearch");
		}
		if (metaKeywordsNode == null) {
			metaKeywordsNode = settingElement.addElement("metaKeywords");
		}
		if (metaDescriptionNode == null) {
			metaDescriptionNode = settingElement.addElement("metaDescription");
		}
		/*
		if (addressNode == null) {
			addressNode = settingElement.addElement("address");
		}
		if (phoneNode == null) {
			phoneNode = settingElement.addElement("phone");
		}
		if (zipCodeNode == null) {
			zipCodeNode = settingElement.addElement("zipCode");
		}
		if (emailNode == null) {
			emailNode = settingElement.addElement("email");
		}
		*/
		if (certtextNode == null) {
			certtextNode = settingElement.addElement("certtext");
		}
		if (currencyTypeNode == null) {
			currencyTypeNode = settingElement.addElement("currencyType");
		}
		if (currencySignNode == null) {
			currencySignNode = settingElement.addElement("currencySign");
		}
		if (currencyUnitNode == null) {
			currencyUnitNode = settingElement.addElement("currencyUnit");
		}
		if (priceScaleNode == null) {
			priceScaleNode = settingElement.addElement("priceScale");
		}
		if (priceRoundTypeNode == null) {
			priceRoundTypeNode = settingElement.addElement("priceRoundType");
		}
		if (storeAlertCountNode == null) {
			storeAlertCountNode = settingElement.addElement("storeAlertCount");
		}
		if (storeFreezeTimeNode == null) {
			storeFreezeTimeNode = settingElement.addElement("storeFreezeTime");
		}
		if (isLoginFailureLockNode == null) {
			isLoginFailureLockNode = settingElement
					.addElement("isLoginFailureLock");
		}
		if (loginFailureLockCountNode == null) {
			loginFailureLockCountNode = settingElement
					.addElement("loginFailureLockCount");
		}
		if (loginFailureLockTimeNode == null) {
			loginFailureLockTimeNode = settingElement
					.addElement("loginFailureLockTime");
		}
		if (isRegisterEnabledNode == null) {
			isRegisterEnabledNode = settingElement
					.addElement("isRegisterEnabled");
		}
		if (watermarkImagePathNode == null) {
			watermarkImagePathNode = settingElement
					.addElement("watermarkImagePath");
		}
		if (watermarkPositionNode == null) {
			watermarkPositionNode = settingElement
					.addElement("watermarkPosition");
		}
		if (watermarkAlphaNode == null) {
			watermarkAlphaNode = settingElement.addElement("watermarkAlpha");
		}
		if (bigGoodsImageWidthNode == null) {
			bigGoodsImageWidthNode = settingElement
					.addElement("bigGoodsImageWidth");
		}
		if (bigGoodsImageHeightNode == null) {
			bigGoodsImageHeightNode = settingElement
					.addElement("bigGoodsImageHeight");
		}
		if (smallGoodsImageWidthNode == null) {
			smallGoodsImageWidthNode = settingElement
					.addElement("smallGoodsImageWidth");
		}
		if (smallGoodsImageHeightNode == null) {
			smallGoodsImageHeightNode = settingElement
					.addElement("smallGoodsImageHeight");
		}
		if (thumbnailGoodsImageWidthNode == null) {
			thumbnailGoodsImageWidthNode = settingElement
					.addElement("thumbnailGoodsImageWidth");
		}
		if (thumbnailGoodsImageHeightNode == null) {
			thumbnailGoodsImageHeightNode = settingElement
					.addElement("thumbnailGoodsImageHeight");
		}
		if (defaultBigGoodsImagePathNode == null) {
			defaultBigGoodsImagePathNode = settingElement
					.addElement("defaultBigGoodsImagePath");
		}
		if (defaultSmallGoodsImagePathNode == null) {
			defaultSmallGoodsImagePathNode = settingElement
					.addElement("defaultSmallGoodsImagePath");
		}
		if (defaultThumbnailGoodsImagePathNode == null) {
			defaultThumbnailGoodsImagePathNode = settingElement
					.addElement("defaultThumbnailGoodsImagePath");
		}
		if (isShowMarketPriceNode == null) {
			isShowMarketPriceNode = settingElement
					.addElement("isShowMarketPrice");
		}
		if (isShowInventoryNode == null) {
			isShowInventoryNode = settingElement.addElement("isShowInventory");
		}
		if (defaultMarketPriceOperatorNode == null) {
			defaultMarketPriceOperatorNode = settingElement
					.addElement("defaultMarketPriceOperator");
		}
		if (defaultMarketPriceNumberNode == null) {
			defaultMarketPriceNumberNode = settingElement
					.addElement("defaultMarketPriceNumber");
		}
		if (smtpFromMailNode == null) {
			smtpFromMailNode = settingElement.addElement("smtpFromMail");
		}
		if (smtpHostNode == null) {
			smtpHostNode = settingElement.addElement("smtpHost");
		}
		if (smtpPortNode == null) {
			smtpPortNode = settingElement.addElement("smtpPort");
		}
		if (smtpUsernameNode == null) {
			smtpUsernameNode = settingElement.addElement("smtpUsername");
		}
		if (smtpPasswordNode == null) {
			smtpPasswordNode = settingElement.addElement("smtpPassword");
		}
		if (scoreTypeNode == null) {
			scoreTypeNode = settingElement.addElement("scoreType");
		}
		if (scoreScaleNode == null) {
			scoreScaleNode = settingElement.addElement("scoreScale");
		}
		if (buildHtmlDelayTimeNode == null) {
			buildHtmlDelayTimeNode = settingElement
					.addElement("buildHtmlDelayTime");
		}
		if (isGzipEnabledNode == null) {
			isGzipEnabledNode = settingElement.addElement("isGzipEnabled");
		}
		if (isInstantMessagingEnabledNode == null) {
			isInstantMessagingEnabledNode = settingElement
					.addElement("isInstantMessagingEnabled");
		}
		if (instantMessagingPositionNode == null) {
			instantMessagingPositionNode = settingElement
					.addElement("instantMessagingPosition");
		}
		if (instantMessagingTitleNode == null) {
			instantMessagingTitleNode = settingElement
					.addElement("instantMessagingTitle");
		}
		if (isLeaveMessageEnabledNode == null) {
			isLeaveMessageEnabledNode = settingElement
					.addElement("isLeaveMessageEnabled");
		}
		if (isLeaveMessageCaptchaEnabledNode == null) {
			isLeaveMessageCaptchaEnabledNode = settingElement
					.addElement("isLeaveMessageCaptchaEnabled");
		}
		if (leaveMessageDisplayTypeNode == null) {
			leaveMessageDisplayTypeNode = settingElement
					.addElement("leaveMessageDisplayType");
		}
		if (isCommentEnabledNode == null) {
			isCommentEnabledNode = settingElement
					.addElement("isCommentEnabled");
		}
		if (isCommentCaptchaEnabledNode == null) {
			isCommentCaptchaEnabledNode = settingElement
					.addElement("isCommentCaptchaEnabled");
		}
		if (commentAuthorityNode == null) {
			commentAuthorityNode = settingElement
					.addElement("commentAuthority");
		}
		if (commentDisplayTypeNode == null) {
			commentDisplayTypeNode = settingElement
					.addElement("commentDisplayType");
		}

        scoreGoodsRatioNode.setText(String.valueOf(setting.getScoreGoodsRatio()));
        scoreRegisterNode.setText(String.valueOf(setting.getScoreRegister()));
        scoreLoginNode.setText(String.valueOf(setting.getScoreLogin()));
        scoreEmailNode.setText(String.valueOf(setting.getScoreEmail()));
        scoreMobileNode.setText(String.valueOf(setting.getScoreMobile()));
        scoreFullInformNode.setText(String.valueOf(setting.getScoreFullInform()));
        scoreCommentNode.setText(String.valueOf(setting.getScoreComment()));
        scoreMarkNode.setText(String.valueOf(setting.getScoreMark()));

        smsEnabledNode.setText(String.valueOf(setting.getSmsEnabled()));
        smsHostNode.setText(setting.getSmsHost());
        smsUsernameNode.setText(setting.getSmsUsername());
        smsPasswordNode.setText(setting.getSmsPassword());

        shopNameNode.setText(setting.getShopName());
		shopUrlNode.setText(setting.getShopUrl());
		shopLogoPathNode.setText(setting.getShopLogoPath());
		hotSearchNode.setText(setting.getHotSearch());
		metaKeywordsNode.setText(setting.getMetaKeywords());
		metaDescriptionNode.setText(setting.getMetaDescription());
		/*
		addressNode.setText(setting.getAddress());
		phoneNode.setText(setting.getPhone());
		zipCodeNode.setText(setting.getZipCode());
		emailNode.setText(setting.getEmail());
		*/
		certtextNode.setText(setting.getCerttext());
		currencyTypeNode.setText(String.valueOf(setting.getCurrencyType()));
		currencySignNode.setText(setting.getCurrencySign());
		currencyUnitNode.setText(setting.getCurrencyUnit());
		priceScaleNode.setText(String.valueOf(setting.getPriceScale()));
		priceRoundTypeNode.setText(String.valueOf(setting.getPriceRoundType()));
		storeAlertCountNode
				.setText(String.valueOf(setting.getStoreAlertCount()));
		storeFreezeTimeNode
				.setText(String.valueOf(setting.getStoreFreezeTime()));
		isLoginFailureLockNode.setText(String.valueOf(setting
				.getIsLoginFailureLock()));
		loginFailureLockCountNode.setText(String.valueOf(setting
				.getLoginFailureLockCount()));
		loginFailureLockTimeNode.setText(String.valueOf(setting
				.getLoginFailureLockTime()));
		isRegisterEnabledNode.setText(String.valueOf(setting
				.getIsRegisterEnabled()));
		watermarkImagePathNode.setText(String.valueOf(setting
				.getWatermarkImagePath()));
		watermarkPositionNode.setText(String.valueOf(setting
				.getWatermarkPosition()));
		watermarkAlphaNode.setText(String.valueOf(setting.getWatermarkAlpha()));
		bigGoodsImageWidthNode.setText(String.valueOf(setting
				.getBigGoodsImageWidth()));
		bigGoodsImageHeightNode.setText(String.valueOf(setting
				.getBigGoodsImageHeight()));
		smallGoodsImageWidthNode.setText(String.valueOf(setting
				.getSmallGoodsImageWidth()));
		smallGoodsImageHeightNode.setText(String.valueOf(setting
				.getSmallGoodsImageHeight()));
		thumbnailGoodsImageWidthNode.setText(String.valueOf(setting
				.getThumbnailGoodsImageWidth()));
		thumbnailGoodsImageHeightNode.setText(String.valueOf(setting
				.getThumbnailGoodsImageHeight()));
		defaultBigGoodsImagePathNode.setText(setting
				.getDefaultBigGoodsImagePath());
		defaultSmallGoodsImagePathNode.setText(setting
				.getDefaultSmallGoodsImagePath());
		defaultThumbnailGoodsImagePathNode.setText(setting
				.getDefaultThumbnailGoodsImagePath());
		isShowMarketPriceNode.setText(String.valueOf(setting
				.getIsShowMarketPrice()));
		isShowInventoryNode
				.setText(String.valueOf(setting.getIsShowInventory()));
		defaultMarketPriceOperatorNode.setText(String.valueOf(setting
				.getDefaultMarketPriceOperator()));
		defaultMarketPriceNumberNode.setText(String.valueOf(setting
				.getDefaultMarketPriceNumber()));
		smtpFromMailNode.setText(setting.getSmtpFromMail());
		smtpHostNode.setText(setting.getSmtpHost());
		smtpPortNode.setText(String.valueOf(setting.getSmtpPort()));
		smtpUsernameNode.setText(setting.getSmtpUsername());
		smtpPasswordNode.setText(setting.getSmtpPassword());
		scoreTypeNode.setText(setting.getScoreType().toString());
		scoreScaleNode.setText(setting.getScoreScale().toString());
		buildHtmlDelayTimeNode.setText(setting.getBuildHtmlDelayTime()
				.toString());
		isGzipEnabledNode.setText(setting.getIsGzipEnabled().toString());
		isInstantMessagingEnabledNode.setText(setting
				.getIsInstantMessagingEnabled().toString());
		instantMessagingPositionNode.setText(setting
				.getInstantMessagingPosition().toString());
		instantMessagingTitleNode.setText(setting.getInstantMessagingTitle());
		isLeaveMessageEnabledNode.setText(setting.getIsLeaveMessageEnabled()
				.toString());
		isLeaveMessageCaptchaEnabledNode.setText(setting
				.getIsLeaveMessageCaptchaEnabled().toString());
		leaveMessageDisplayTypeNode.setText(setting
				.getLeaveMessageDisplayType().toString());
		isCommentEnabledNode.setText(setting.getIsCommentEnabled().toString());
		isCommentCaptchaEnabledNode.setText(setting
				.getIsCommentCaptchaEnabled().toString());
		commentAuthorityNode.setText(setting.getCommentAuthority().toString());
		commentDisplayTypeNode.setText(setting.getCommentDisplayType()
				.toString());

		try {
			OutputFormat outputFormat = OutputFormat.createPrettyPrint();// 设置XML文档输出格式
			outputFormat.setEncoding("UTF-8");// 设置XML文档的编码类型
			outputFormat.setIndent(true);// 设置是否缩进
			outputFormat.setIndent("	");// 以TAB方式实现缩进
			outputFormat.setNewlines(true);// 设置是否换行
			XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(
					rzrkXmlFile), outputFormat);
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新系统配置信息
	 * 
	 * @param setting
	 */
	public static void updateSetting(Setting setting) {
		writeSetting(setting);
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil
				.getBean(CACHE_MANAGER_BEAN_NAME);
		generalCacheAdministrator.flushEntry(SETTING_CACHE_KEY);
	}

	/**
	 * 刷新系统配置信息
	 * 
	 */
	public void flush() {
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil
				.getBean(CACHE_MANAGER_BEAN_NAME);
		generalCacheAdministrator.flushEntry(SETTING_CACHE_KEY);
	}

	/**
	 * 设置价格精确位数
	 * 
	 */
	public static BigDecimal setPriceScale(BigDecimal price) {
		if (price != null) {
			Integer priceScale = getSetting().getPriceScale();
			RoundType priceRoundType = getSetting().getPriceRoundType();
			if (priceRoundType == RoundType.roundHalfUp) {
				return price.setScale(priceScale, BigDecimal.ROUND_HALF_UP);
			} else if (priceRoundType == RoundType.roundUp) {
				return price.setScale(priceScale, BigDecimal.ROUND_UP);
			} else {
				return price.setScale(priceScale, BigDecimal.ROUND_DOWN);
			}
		}
		return null;
	}

	/**
	 * 获取货币格式字符串
	 * 
	 */
	public static String getCurrencyFormat() {
		Integer priceScale = getSetting().getPriceScale();
		String currencySign = getSetting().getCurrencySign();
		String currencyUnit = getSetting().getCurrencyUnit();
		if (priceScale == 0) {
			return "'" + currencySign + "'#0'" + currencyUnit + "'";
		} else if (priceScale == 1) {
			return "'" + currencySign + "'#0.0'" + currencyUnit + "'";
		} else if (priceScale == 2) {
			return "'" + currencySign + "'#0.00'" + currencyUnit + "'";
		} else if (priceScale == 3) {
			return "'" + currencySign + "'#0.000'" + currencyUnit + "'";
		} else if (priceScale == 4) {
			return "'" + currencySign + "'#0.0000'" + currencyUnit + "'";
		} else {
			return "'" + currencySign + "'#0.00000'" + currencyUnit + "'";
		}
	}

	/**
	 * 获取货币格式化
	 * 
	 */
	public static String currencyFormat(BigDecimal price) {
		String currencyFormat = getCurrencyFormat();
		NumberFormat numberFormat = new DecimalFormat(currencyFormat);
		return numberFormat.format(price);
	}

	/**
	 * 根据销售价获取默认市场价
	 * 
	 */
	public static BigDecimal getDefaultMarketPrice(BigDecimal price) {
		Setting setting = getSetting();
		Operator defaultMarketPriceOperator = setting
				.getDefaultMarketPriceOperator();
		BigDecimal defaultMarketPriceNumber = setting
				.getDefaultMarketPriceNumber();
		if (defaultMarketPriceOperator == Operator.add) {
			return price.add(defaultMarketPriceNumber);
		} else if (defaultMarketPriceOperator == Operator.subtract) {
			return price.subtract(defaultMarketPriceNumber);
		} else if (defaultMarketPriceOperator == Operator.multiply) {
			return price.multiply(defaultMarketPriceNumber);
		} else if (defaultMarketPriceOperator == Operator.divide) {
			return price.divide(defaultMarketPriceNumber, 5,
					BigDecimal.ROUND_HALF_UP);
		}
		return null;
	}

}