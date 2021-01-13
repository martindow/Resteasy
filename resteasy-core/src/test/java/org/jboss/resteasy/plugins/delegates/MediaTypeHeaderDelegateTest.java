package org.jboss.resteasy.plugins.delegates;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import org.junit.Before;
import org.junit.Test;

public class MediaTypeHeaderDelegateTest {

  private MediaTypeHeaderDelegate delegate;

  @Before
  public void setUp() throws Exception {
    delegate = new MediaTypeHeaderDelegate();
  }

  @Test
  public void testParseStripsTrailingSemicolonWhenParsingDodgyContentType() {
    MediaTypeHeaderDelegate.parse("application/json;");

    assertEquals("application/json", delegate.toString(new MediaType("application", "json")));
  }

  @Test
  public void testParseHandlesMediaTypeWithCharset() {
    MediaTypeHeaderDelegate.parse("application/json; charset=utf-8");

    assertEquals("application/json;charset=utf-8", delegate.toString(new MediaType("application", "json", "utf-8")));
  }

  @Test
  public void testParseHandlesMediaTypeWithCharsetAndAnotherParameter() {
    MediaTypeHeaderDelegate.parse("application/json; charset=utf-8; a=bobbytables");

    Map<String, String> parameters = new HashMap<>();
    parameters.put("charset", "utf-8");
    parameters.put("a", "bobbytables");
    assertEquals("application/json;a=bobbytables;charset=utf-8", delegate.toString(new MediaType("application", "json", parameters)));
  }
}