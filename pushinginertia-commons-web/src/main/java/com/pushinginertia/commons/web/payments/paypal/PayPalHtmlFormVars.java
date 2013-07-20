/* Copyright (c) 2011-2013 Pushing Inertia
 * All rights reserved.  http://pushinginertia.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pushinginertia.commons.web.payments.paypal;

/**
 * Enumeration of the various form variables PayPal supports when submitting a payment request via an HTML form.
 * Source:
 * <a href="https://developer.paypal.com/webapps/developer/docs/classic/paypal-payments-standard/integration-guide/Appx_websitestandard_htmlvariables/">https://developer.paypal.com/webapps/developer/docs/classic/paypal-payments-standard/integration-guide/Appx_websitestandard_htmlvariables/</a>
 */
public final class PayPalHtmlFormVars {
	/**
	 * Required.
	 * Your PayPal ID or an email address associated with your PayPal account. Email addresses must be confirmed.
	 */
	public static final String BUSINESS = "business";
	/**
	 * Optional.
	 * Description of item being sold. If you are collecting aggregate payments, the value can be a summary of all items
	 * purchased, a tracking number, or a generic term such as "subscription." If this variable is omitted, buyers see a
	 * field in which they can enter the item name.
	 * Max 127 chars.
	 */
	public static final String ITEM_NAME = "item_name";
	/**
	 * Pass-through variable for you to track product or service purchased or the contribution made. The value you
	 * specify is passed back to you upon payment completion. This variable is required if you want PayPal to track
	 * inventory or track profit and loss for the item the button sells.
	 * Max 127 chars.
	 */
	public static final String ITEM_NUMBER = "item_number";
	/**
	 * The price or amount of the product, service, or contribution, not including shipping, handling, or tax. If this
	 * variable is omitted from Buy Now or Donate buttons, buyers enter their own amount at the time of payment.
	 * <ul>
	 *     <li>Required for Add to Cart buttons</li>
	 *     <li>Optional for Buy Now and Donate buttons</li>
	 *     <li>Not used with Subscribe or Buy Gift Certificate buttons</li>
	 * </ul>
	 */
	public static final String AMOUNT = "amount";
	/**
	 * Optional.
	 * The currency of the payment. The default is USD. See
	 * <a href="https://developer.paypal.com/webapps/developer/docs/classic/api/currency_codes/">https://developer.paypal.com/webapps/developer/docs/classic/api/currency_codes/</a>.
	 * 3 chars.
	 */
	public static final String CURRENCY_CODE = "currency_code";
	/**
	 * Optional.
	 * Pass-through variable for your own tracking purposes, which buyers do not see.
	 * Max 256 chars.
	 */
	public static final String CUSTOM = "custom";
	/**
	 * Optional.
	 * The URL to which PayPal redirects buyers' browser after they complete their payments. For example, specify a URL
	 * on your site that displays a "Thank you for your payment" page.
	 * Default: PayPal redirects the browser to a PayPal webpage.
	 * Max 1024 chars.
	 */
	public static final String RETURN = "return";
	/**
	 * Optional.
	 * A URL to which PayPal redirects the buyers' browsers if they cancel checkout before completing their payments.
	 * For example, specify a URL on your website that displays a "Payment Cancelled" page.
	 * Default: PayPal redirects the browser to a PayPal webpage.
	 * Max 1024 chars.
	 */
	public static final String CANCEL_RETURN = "cancel_return";
	/**
	 * Optional.
	 * The URL to which PayPal posts information about the payment, in the form of Instant Payment Notification
	 * messages.
	 * Max 255 chars.
	 */
	public static final String NOTIFY_URL = "notify_url";
	/**
	 * Optional.
	 * The locale of the login or sign-up page, which may have the specific country's language available, depending on
	 * localization. If unspecified, PayPal determines the locale by using a cookie in the subscriber's browser. If
	 * there is no PayPal cookie, the default locale is US.
	 * The following two-character country codes are supported by PayPal:
	 * <ul>
	 *     <li>AU – Australia</li>
	 *     <li>AT – Austria</li>
	 *     <li>BE – Belgium</li>
	 *     <li>BR – Brazil</li>
	 *     <li>CA – Canada</li>
	 *     <li>CH – Switzerland</li>
	 *     <li>CN – China</li>
	 *     <li>DE – Germany</li>
	 *     <li>ES – Spain</li>
	 *     <li>GB – United Kingdom</li>
	 *     <li>FR – France</li>
	 *     <li>IT – Italy</li>
	 *     <li>NL – Netherlands</li>
	 *     <li>PL – Poland</li>
	 *     <li>PT – Portugal</li>
	 *     <li>RU – Russia</li>
	 *     <li>US – United States</li>
	 *     <li>da_DK – Danish (for Denmark only)</li>
	 *     <li>he_IL – Hebrew (all)</li>
	 *     <li>id_ID – Indonesian (for Indonesia only)</li>
	 *     <li>ja_JP – Japanese (for Japan only)</li>
	 *     <li>no_NO – Norwegian (for Norway only)</li>
	 *     <li>pt_BR – Brazilian Portuguese (for Portugal and Brazil only)</li>
	 *     <li>ru_RU – Russian (for Lithuania, Latvia, and Ukraine only)</li>
	 *     <li>sv_SE – Swedish (for Sweden only)</li>
	 *     <li>th_TH – Thai (for Thailand only)</li>
	 *     <li>tr_TR – Turkish (for Turkey only)</li>
	 *     <li>zh_CN – Simplified Chinese (for China only)</li>
	 *     <li>zh_HK – Traditional Chinese (for Hong Kong only)</li>
	 *     <li>zh_TW – Traditional Chinese (for Taiwan only)</li>
	 * </ul>
	 */
	public static final String LC = "lc";
	/**
	 * Optional.
	 * Sets shipping and billing country.
	 * 2 chars.
	 */
	public static final String COUNTRY = "country";
	/**
	 * Optional.
	 * Street (1 of 2 fields).
	 * Max 100 chars.
	 */
	public static final String ADDRESS_1 = "address1";
	/**
	 * Optional.
	 * City.
	 * Max 40 chars.
	 */
	public static final String CITY = "city";
	/**
	 * Optional.
	 * First name.
	 * Max 32 chars.
	 */
	public static final String FIRST_NAME = "first_name";
	/**
	 * Optional.
	 * Last name.
	 * Max 32 chars.
	 */
	public static final String LAST_NAME = "last_name";
	/**
	 * Optional.
	 * U.S. state.
	 * 2 chars.
	 */
	public static final String STATE = "state";
	/**
	 * Optional.
	 * Postal code.
	 * Max 32 chars.
	 */
	public static final String ZIP = "zip";
	/**
	 * Optional.
	 * Email address.
	 * Max 127 chars.
	 */
	public static final String EMAIL = "email";

	private PayPalHtmlFormVars() {}
}
