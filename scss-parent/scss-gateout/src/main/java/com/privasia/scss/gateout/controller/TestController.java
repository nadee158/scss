package com.privasia.scss.gateout.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.cosmos.dto.TestDto;
import com.privasia.scss.cosmos.repository.TestRepository;


@RestController
@RequestMapping("api")
public class TestController {

  @Autowired
  private TestRepository testRepository;

  @RequestMapping(value = "/test/{containerNo}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<TestDto>> scanCardByCardNo(@PathVariable("containerNo") String containerNo) {
    System.out.println("CAME HERE CONTROLLER :" + containerNo);
    List<TestDto> list = testRepository.testQuery(containerNo);
    return new ResponseEntity<List<TestDto>>(list, HttpStatus.OK);
  }

}
