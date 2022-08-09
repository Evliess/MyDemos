package com.my.service.dp.abstractfactory;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

interface FixProtocol {
  void connect();
}

interface Report {
  void connect();
}

public class AbstractFactoryTest {
  final Logger logger = LoggerFactory.getLogger(AbstractFactoryTest.class);

  @Test
  public void test() {
    HtmlReportFactory r = (HtmlReportFactory) FactoryProvider.getFactory("html");
    r.buildFactory().connect();

    QuickFixFactory fixProtocol = (QuickFixFactory) FactoryProvider.getFactory("quickFix");
    fixProtocol.buildFactory().connect();
  }

}

class QuickFix implements FixProtocol {
  final Logger logger = LoggerFactory.getLogger(QuickFix.class);

  @Override
  public void connect() {
    logger.info("QuickFix Connect");
  }
}

class HtmlReport implements Report {
  final Logger logger = LoggerFactory.getLogger(QuickFix.class);

  @Override
  public void connect() {
    logger.info("HtmlReport Connect");
  }
}

abstract class AbstractFactory<T> {
  abstract T buildFactory();
}

class QuickFixFactory extends AbstractFactory<FixProtocol> {
  @Override
  public FixProtocol buildFactory() {
    return new QuickFix();
  }
}

class HtmlReportFactory extends AbstractFactory<Report> {
  @Override
  public Report buildFactory() {
    return new HtmlReport();
  }
}

class FactoryProvider {
  public static AbstractFactory getFactory(String type) {
    switch (type) {
      case "html":
        return new HtmlReportFactory();
      case "quickFix":
        return new QuickFixFactory();
      default:
        return null;
    }
  }
}




