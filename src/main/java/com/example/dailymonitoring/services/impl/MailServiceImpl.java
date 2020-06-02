package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.services.MailService;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

  private final JavaMailSender emailSender;

  public MailServiceImpl(JavaMailSender emailSender) {
    this.emailSender = emailSender;
  }

  @SneakyThrows
  @Override
  public void sendMessage(String to, String subject, String text) {
    MimeMessage mimeMessage = emailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

    String htmlMsg = "<div class=\"es-wrapper-color\">\n" +
        "        <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
        "            <tbody>\n" +
        "                <tr>\n" +
        "                    <td class=\"esd-email-paddings\" valign=\"top\">\n" +
        "                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header\" align=\"center\">\n" +
        "                            <tbody>\n" +
        "                                <tr>\n" +
        "                                    <td class=\"esd-stripe\" esd-custom-block-id=\"3089\" align=\"center\">\n" +
        "                                        <table class=\"es-header-body\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
        "                                            <tbody>\n" +
        "                                                <tr>\n" +
        "                                                    <td class=\"esd-structure es-p10t es-p10b es-p10r es-p10l\" align=\"left\">\n" +
        "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
        "                                                            <tbody>\n" +
        "                                                                <tr>\n" +
        "                                                                    <td class=\"esd-container-frame\" width=\"580\" valign=\"top\" align=\"center\">\n" +
        "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
        "                                                                            <tbody>\n" +
        "                                                                                <tr>\n" +
        "                                                                                    <td class=\"esd-block-image\" align=\"center\" style=\"font-size:0\"><a href=\"https://viewstripo.email\" target=\"_blank\"><img src=\"https://i.ibb.co/9Hvkmqm/logo-transparent.png\" alt=\"Smart home logo\" title=\"Smart home logo\" width=\"109\"></a></td>\n" +
        "                                                                                </tr>\n" +
        "                                                                            </tbody>\n" +
        "                                                                        </table>\n" +
        "                                                                    </td>\n" +
        "                                                                </tr>\n" +
        "                                                            </tbody>\n" +
        "                                                        </table>\n" +
        "                                                    </td>\n" +
        "                                                </tr>\n" +
        "                                            </tbody>\n" +
        "                                        </table>\n" +
        "                                    </td>\n" +
        "                                </tr>\n" +
        "                            </tbody>\n" +
        "                        </table>\n" +
        "                        <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
        "                            <tbody>\n" +
        "                                <tr>\n" +
        "                                    <td class=\"esd-stripe\" esd-custom-block-id=\"3109\" align=\"center\">\n" +
        "                                        <table class=\"es-content-body\" style=\"background-color: #ffffff;\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\">\n" +
        "                                            <tbody>\n" +
        "                                                <tr>\n" +
        "                                                    <td class=\"esd-structure es-p20t es-p20b es-p40r es-p40l\" esd-general-paddings-checked=\"true\" align=\"left\">\n" +
        "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
        "                                                            <tbody>\n" +
        "                                                                <tr>\n" +
        "                                                                    <td class=\"esd-container-frame\" width=\"520\" valign=\"top\" align=\"center\">\n" +
        "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
        "                                                                            <tbody>\n" +
        "                                                                                <tr>\n" +
        "                                                                                    <td class=\"esd-block-text\" align=\"left\">\n" +
        "                                                                                        <h1 style=\"color: #4a7eb0;\">Confirm registration</h1>\n" +
        "                                                                                    </td>\n" +
        "                                                                                </tr>\n" +
        "                                                                                <tr>\n" +
        "                                                                                    <td class=\"esd-block-spacer es-p5t es-p20b\" align=\"left\" style=\"font-size:0\">\n" +
        "                                                                                        <table width=\"5%\" height=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n" +
        "                                                                                            <tbody>\n" +
        "                                                                                                <tr>\n" +
        "                                                                                                    <td style=\"border-bottom: 2px solid #999999; background: rgba(0, 0, 0, 0) none repeat scroll 0% 0%; height: 1px; width: 100%; margin: 0px;\"></td>\n" +
        "                                                                                                </tr>\n" +
        "                                                                                            </tbody>\n" +
        "                                                                                        </table>\n" +
        "                                                                                    </td>\n" +
        "                                                                                </tr>\n" +
        "                                                                                <tr>\n" +
        "                                                                                    <td class=\"esd-block-text es-p10b\" align=\"left\">\n" +
        "                                                                                        <p><span style=\"font-size: 16px; line-height: 150%;\">Hello,</span></p>\n" +
        "                                                                                    </td>\n" +
        "                                                                                </tr>\n" +
        "                                                                                <tr>\n" +
        "                                                                                    <td class=\"esd-block-text\" align=\"left\">\n" +
        "                                                                                        <p>To get started, we need to confirm that " + to + " is your email address, so please click this button to finish creating your account.</p>\n" +
        "                                                                                    </td>\n" +
        "                                                                                </tr>\n" +
        "                                                                                <tr>\n" +
        "                                                                                    <!-- <td class=\"esd-block-button es-p20t es-p20b\" align=\"left\"><span class=\"es-button-border\"><a href=\"https://viewstripo.email/\" class=\"es-button\" target=\"_blank\" style=\"border-width: 10px 25px;\">Active account</a></span></td> -->\n" +
        "                                                </tr>\n" +
        "                                                                                <td><a href='" + text + "' style=\"min-width: 196px;\n" +
        "                                                                                cursor: pointer;\n" +
        "                                                                                border-top: 13px solid;\n" +
        "                                                                                border-bottom: 13px solid;\n" +
        "                                                                                border-right: 24px solid;\n" +
        "                                                                                border-left: 24px solid;\n" +
        "                                                                                border-color: #2ea664;\n" +
        "                                                                                border-radius: 4px;\n" +
        "                                                                                background-color: #2ea664;\n" +
        "                                                                                color: #ffffff;\n" +
        "                                                                                font-size: 18px;\n" +
        "                                                                                line-height: 18px;\n" +
        "                                                                                text-shadow: 0 1px 1px #2e9a5f;\n" +
        "                                                                                display: inline-block;\n" +
        "                                                                                text-align: center;\n" +
        "                                                                                font-weight: 900;\n" +
        "                                                                                text-decoration: none !important;\">Activate account</a></td>\n" +
        "                                                                                </tr>\n" +
        "                                                                                <tr>\n" +
        "                                                                                    <td class=\"esd-block-text\" align=\"left\">\n" +
        "                                                                                        <p>If you need help visit the <a href=\"" + text + "\">Help</a> page or <a target=\"_blank\" href=\"https://viewstripo.email/\">contact us</a>.</p>\n" +
        "                                                                                    </td>\n" +
        "                                                                                </tr>\n" +
        "                                                                            </tbody>\n" +
        "                                                                        </table>\n" +
        "                                                                    </td>\n" +
        "                                                                </tr>\n" +
        "                                                            </tbody>\n" +
        "                                                        </table>\n" +
        "                                                    </td>\n" +
        "                                                </tr>\n" +
        "                                            </tbody>\n" +
        "                                        </table>\n" +
        "                                    </td>\n" +
        "                                </tr>\n" +
        "                            </tbody>\n" +
        "                        </table>\n" +
        "                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer\" align=\"center\">\n" +
        "                            <tbody>\n" +
        "                                <tr>\n" +
        "                                    <td class=\"esd-stripe\" esd-custom-block-id=\"3104\" align=\"center\">\n" +
        "                                        <table class=\"es-footer-body\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
        "                                            <tbody>\n" +
        "                                                <tr>\n" +
        "                                                    <td class=\"esd-structure es-p15b es-p20r es-p20l\" esd-general-paddings-checked=\"false\" align=\"left\">\n" +
        "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
        "                                                            <tbody>\n" +
        "                                                                <tr>\n" +
        "                                                                    <td class=\"esd-container-frame\" width=\"560\" valign=\"top\" align=\"center\">\n" +
        "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
        "                                                                            <tbody>\n" +
        "                                                                                <tr>\n" +
        "                                                                                    <td esdev-links-color=\"#333333\" align=\"left\" class=\"esd-block-text\">\n" +
        "                                                                                        <p style=\"font-size: 12px; line-height: 150%;\">You are receiving this email because you have registered on our site.</p>\n" +
        "                                                                                </tr>\n" +
        "                                                                            </tbody>\n" +
        "                                                                        </table>\n" +
        "                                                                    </td>\n" +
        "                                                                </tr>\n" +
        "                                                            </tbody>\n" +
        "                                                        </table>\n" +
        "                                                    </td>\n" +
        "                                                </tr>\n" +
        "                                            </tbody>\n" +
        "                                        </table>\n" +
        "                                    </td>\n" +
        "                                </tr>\n" +
        "                            </tbody>\n" +
        "                        </table>\n" +
        "                    </td>\n" +
        "                </tr>\n" +
        "            </tbody>\n" +
        "        </table>\n" +
        "    </div>";
    helper.setText(htmlMsg, true);
    helper.setFrom("no-reply@dailymonitoring.com", "Daily Monitoring");
    helper.setTo(to);
    helper.setSubject(subject);
    emailSender.send(mimeMessage);
  }
}
