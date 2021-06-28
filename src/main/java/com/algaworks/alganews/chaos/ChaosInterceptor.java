package com.algaworks.alganews.chaos;

import com.algaworks.alganews.common.domain.BusinessException;
import com.algaworks.alganews.common.domain.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Profile("chaos-request")
@AllArgsConstructor
public class ChaosInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response,
							 Object handler) throws Exception {
		if (doesNotContainsChaosParameter(request))
			return true;
		
		return getChaosAction(request).preHandle(response);
	}
	
	private ChaosAction getChaosAction(HttpServletRequest request) {
		String parameter = request.getParameter("chaos");
		return getEnumValueOrDefault(parameter);
	}
	
	private ChaosAction getEnumValueOrDefault(String parameter) {
		String formattedParameter = parameter.toUpperCase().replace("-", "_");
		return EnumUtils.getEnum(ChaosAction.class, formattedParameter, ChaosAction.DEFAULT);
	}
	
	private boolean containsChaosParameter(HttpServletRequest request) {
		return StringUtils.isNotBlank(request.getParameter("chaos"));
	}
	
	private boolean doesNotContainsChaosParameter(HttpServletRequest request) {
		return !containsChaosParameter(request);
	}
	
	private enum ChaosAction {
		DEFAULT {
			@Override
			boolean preHandle(HttpServletResponse response) {
				throw new BusinessException("Parâmetro chaos inválido, os tipos válidos são service, " +
						"model, internal, timeout e not-found.");
			}
		},
		INTERNAL {
			@Override
			boolean preHandle(HttpServletResponse response) {
				throw new RuntimeException("Erro interno.");
			}
		},
		SERVICE {
			@Override
			boolean preHandle(HttpServletResponse response) {
				throw new BusinessException("Erro de regra de negócio.");
			}
		},
		NOT_FOUND {
			@Override
			boolean preHandle(HttpServletResponse response) {
				throw new EntityNotFoundException("Não encontrado.");
			}
		},
		MODEL {
			@Override
			boolean preHandle(HttpServletResponse response) {
				try {
					PrintWriter writer = response.getWriter();
					writer.println("{}");
					writer.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				return false;
			}
		},
		TIMEOUT {
			@Override
			boolean preHandle(HttpServletResponse response) {
				try {
					Thread.sleep(120000L);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				return true;
			}
		};
		
		abstract boolean preHandle(HttpServletResponse response);
	}

}
