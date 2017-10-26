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

import com.facebook.ads.sdk.APIContext;
import com.facebook.ads.sdk.APIException;
import com.facebook.ads.sdk.Ad;
import com.facebook.ads.sdk.AdAccount;
import com.facebook.ads.sdk.AdCreative;
import com.facebook.ads.sdk.AdPreview;
import com.facebook.ads.sdk.AdSet;
import com.facebook.ads.sdk.Campaign;
import com.facebook.ads.sdk.Targeting;
import com.facebook.ads.sdk.TargetingGeoLocation;

import java.util.Arrays;

public class SAMPLE_CODE {
    public static void main(String args[]) throws APIException {

        String access_token = "EAAEguIrV5UUBAJD2g2BeZBZBkMCIazYeL9ZBLUZCw07smR8HVkZCaEloOldSHtb3MRqPxU7y2atdCt2YhxTPrZCJIkHrpiA5HfsxvL5ZClTQTjRowSSvzJGkG2xIAAVZAMZAwrcQ60ZBWz8PZCtZBnfeOUuKYDvMJMOJdwIjZCkTEVvZA6CtcJSAuTJgxDc834VtZBtI1HrZCBZCmWRc2LvplDZCvHoRMCNj33lRxY5t0ZD";
        String ad_account_id = "110309716397592";
        String app_secret = "9d8cef5e79cc3d7c4a7146cc16c534df";
        String page_id = "122771645063746";
        APIContext context = new APIContext(access_token).enableDebug(true);

        Campaign campaign = new AdAccount(ad_account_id, context).createCampaign()
                .setObjective(Campaign.EnumObjective.VALUE_PAGE_LIKES)
                .setStatus(Campaign.EnumStatus.VALUE_PAUSED)
                .setBuyingType("AUCTION")
                .setName("My Campaign")
                .execute();
        String campaign_id = campaign.getId();
        AdSet adSet = new AdAccount(ad_account_id, context).createAdSet()
                .setStatus(AdSet.EnumStatus.VALUE_PAUSED)
                .setTargeting(
                        new Targeting()
                                .setFieldGeoLocations(
                                        new TargetingGeoLocation()
                                                .setFieldCountries(Arrays.asList("US"))
                                )
                )
                .setDailyBudget(1000L)
                .setBillingEvent(AdSet.EnumBillingEvent.VALUE_IMPRESSIONS)
                .setBidAmount(20L)
                .setCampaignId(campaign_id)
                .setOptimizationGoal(AdSet.EnumOptimizationGoal.VALUE_PAGE_LIKES)
                .setPromotedObject("{\"page_id\": " + page_id + "}")
                .setName("My AdSet")
                .execute();
        String ad_set_id = adSet.getId();
        AdCreative creative = new AdAccount(ad_account_id, context).createAdCreative()
                .setBody("Like My Page")
                .setImageUrl("http://www.facebookmarketingdevelopers.com/static/images/resource_1.jpg")
                .setName("My Creative")
                .setObjectId(page_id)
                .setTitle("My Page Like Ad")
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
