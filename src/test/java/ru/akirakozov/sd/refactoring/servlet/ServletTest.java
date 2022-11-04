package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public abstract class ServletTest {

    @Mock
    protected HttpServletRequest httpServletRequest;

    @Mock
    protected HttpServletResponse httpServletResponse;

    private AutoCloseable mockitoSession;

    protected Map<String, String> parameters;

    protected StringWriter responseWriter;

    @Before
    public void init() throws Exception {
        mockitoSession = MockitoAnnotations.openMocks(this);
        parameters = new HashMap<>();

        Mockito.doAnswer(invocation -> parameters.get((String) invocation.getArguments()[0]))
                .when(httpServletRequest)
                .getParameter(Mockito.anyString());

        Mockito.doReturn(new PrintWriter(responseWriter))
                .when(httpServletResponse)
                .getWriter();
    }

    protected void expectContentType(final String contentType) {
        Mockito.doAnswer(invocation -> {
                    Assert.assertEquals(contentType, invocation.getArguments()[0]);
                    return null;
                })
                .when(httpServletResponse)
                .setContentType(Mockito.anyString());
    }

    protected void expectStatus(final int statusCode) {
        Mockito.doAnswer(invocation -> {
                    Assert.assertEquals(statusCode, invocation.getArguments()[0]);
                    return null;
                })
                .when(httpServletResponse)
                .setStatus(Mockito.anyInt());
    }

    @After
    public void tearDown() throws Exception {
        mockitoSession.close();
    }
}
