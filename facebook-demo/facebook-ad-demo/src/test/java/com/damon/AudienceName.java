package com.damon; /**
 * Copyright (c) 2015-present, Facebook, Inc. All rights reserved.
 * <p>
 * You are hereby granted a non-exclusive, worldwide, royalty-free license to
 * use, copy, modify, and distribute this software in source code or binary
 * form for use in connection with the web services and APIs provided by
 * Facebook.
 * <p>
 * As with any software that integrates with the Facebook platform, your use
 * of this software is subject to the Facebook Developer Principles and
 * Policies [http://developers.facebook.com/policy/]. This copyright notice
 * shall be included in all copies or substantial portions of the software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

import com.facebook.ads.sdk.*;

import java.io.File;
import java.util.Arrays;

public class AudienceName {
    public static void main(String args[]) throws APIException {

        String access_token = "EAAEguIrV5UUBAJD2g2BeZBZBkMCIazYeL9ZBLUZCw07smR8HVkZCaEloOldSHtb3MRqPxU7y2atdCt2YhxTPrZCJIkHrpiA5HfsxvL5ZClTQTjRowSSvzJGkG2xIAAVZAMZAwrcQ60ZBWz8PZCtZBnfeOUuKYDvMJMOJdwIjZCkTEVvZA6CtcJSAuTJgxDc834VtZBtI1HrZCBZCmWRc2LvplDZCvHoRMCNj33lRxY5t0ZD";
        String app_secret = "9d8cef5e79cc3d7c4a7146cc16c534df";
        String ad_account_id = "110309716397592";
        String audience_name = "30天内";
        String audience_retention_days = "30";
        String pixel_id = "132343040821844";
        APIContext context = new APIContext(access_token).enableDebug(true);

        Campaign campaign = new AdAccount(ad_account_id, context).createCampaign()
                .setObjective(Campaign.EnumObjective.VALUE_LINK_CLICKS)
                .setStatus(Campaign.EnumStatus.VALUE_PAUSED)
                .setBuyingType("AUCTION")
                .setName("My Campaign")
                .execute();
        String campaign_id = campaign.getId();
        CustomAudience customAudience = new AdAccount(ad_account_id, context).createCustomAudience()
                .setName(audience_name)
                .setPixelId(pixel_id)
                .setPrefill(true)
                .setRule("{\"url\":{\"i_contains\":\"\"}}")
                .setSubtype(CustomAudience.EnumSubtype.VALUE_WEBSITE)
                .setRetentionDays(audience_retention_days)
                .execute();
        String custom_audience_id = customAudience.getId();
        AdSet adSet = new AdAccount(ad_account_id, context).createAdSet()
                .setStatus(AdSet.EnumStatus.VALUE_PAUSED)
                .setTargeting(
                        new Targeting()
                                .setFieldCustomAudiences(Arrays.asList(
                                        new IDName()
                                                .setFieldId(custom_audience_id)
                                ))
                                .setFieldGeoLocations(
                                        new TargetingGeoLocation()
                                                .setFieldCountries(Arrays.asList("US"))
                                )
                )
                .setName("My AdSet")
                .setBillingEvent(AdSet.EnumBillingEvent.VALUE_IMPRESSIONS)
                .setBidAmount(20L)
                .setCampaignId(campaign_id)
                .setOptimizationGoal(AdSet.EnumOptimizationGoal.VALUE_REACH)
                .setDailyBudget(1000L)
                .execute();
        String ad_set_id = adSet.getId();
        AdCreative creative = new AdAccount(ad_account_id, context).createAdCreative()
                .setBody("Like My Page")
                .setName("My Creative")
                .setTitle("My Page Like Ad")
                .setObjectUrl("www.facebook.com")
                .setLinkUrl("www.facebook.com")
                .setImageUrl("http://www.facebookmarketingdevelopers.com/static/images/resource_1.jpg")
                .execute();
        String creative_id = creative.getId();
        Ad ad = new AdAccount(ad_account_id, context).createAd()
                .setStatus(Ad.EnumStatus.VALUE_PAUSED)
                .setAdsetId(ad_set_id)
                .setName("My Ad")
                .setCreative(
                        new AdCreative()
                                .setFieldId(creative_id)
                )
                .execute();
        String ad_id = ad.getId();
        new Ad(ad_id, context).getPreviews()
                .setAdFormat(AdPreview.EnumAdFormat.VALUE_DESKTOP_FEED_STANDARD)
                .execute();
    }
}
