package com.privasia.scss.scancard.service;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.privasia.scss.common.util.UserIpAddressUtil;
import com.privasia.scss.core.config.WebSecurityConfig;
import com.privasia.scss.core.dto.SmartCardUserDTO;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.security.jwt.extractor.TokenExtractor;
import com.privasia.scss.core.security.util.SecurityContext;
import com.privasia.scss.core.security.util.SecurityHelper;

@Service("cardService")
public class CardService {

  @Value("${scss.client.port}")
  private String scssClientPort;

  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private TokenExtractor tokenExtractor;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public SmartCardUserDTO selectSCUInfo(Long cardID, Long cardNo, HttpServletRequest httpRequest) {

    if (cardID == null)
      cardID = new Long(0);
    if (cardNo == null)
      cardNo = new Long(0);

    Optional<SmartCardUserDTO> smartCardUser = cardRepository.findSCUInfoByCardIdOrNo(cardID, cardNo);


    String webIPAddress = UserIpAddressUtil.getUserIp(httpRequest);
    String baseUrl = UserIpAddressUtil.getBaseUrl(httpRequest);

    if (smartCardUser.isPresent()) {
      SmartCardUserDTO cu = smartCardUser.orElse(null);
      if (!(cu == null)) {
        String clientUnitNo = getClientUnitNoFromClientService(webIPAddress, baseUrl);
        cu.setClientUnitNo(clientUnitNo);
        return cu;
      }
    }

    throw new ResultsNotFoundException("Requested SCU Info not found!");

  }

  public String getClientUnitNoFromClientService(String webIPAddress, String baseUrl) {
    try {
      System.out.println("webIPAddress :" + webIPAddress);
      System.out.println("baseUrl :" + baseUrl);
      SecurityContext securityContext = SecurityHelper.getSecurityContext(tokenExtractor);
      String url = /* "http://localhost:8080" */ baseUrl + "/scss-client/api/client/" + webIPAddress + "/unitNo";
      System.out.println("final Url :" + url);
      System.out.println("securityContext.getToken() :" + securityContext.getToken());

      RestTemplate restTemplate = new RestTemplate();

      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
      headers.set(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM, "Bearer " + securityContext.getToken());


      HttpEntity<?> entity = new HttpEntity<>(headers);


      HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
      System.out.println("response :" + response);
      System.out.println("response.getBody() :" + response.getBody());

      return response.getBody();


    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public String selectCompanyCode(String cardNo) {
    Optional<Card> card = cardRepository.findByCardNo(Long.parseLong(cardNo));
    return getCompanyCodeFromCard(card.orElse(null));
  }

  public String selectCompanyCode(Long cardID) {
    Optional<Card> card = cardRepository.findOne(cardID);
    return getCompanyCodeFromCard(card.orElse(null));
  }

  private String getCompanyCodeFromCard(Card card) {
    if (!(card == null)) {
      if (!(card.getCompany() == null)) {
        return card.getCompany().getCompanyCode();
      } else {
        throw new ResultsNotFoundException("Card was not found!");
      }
    } else {
      throw new ResultsNotFoundException("Card was not found!");
    }
  }


}
