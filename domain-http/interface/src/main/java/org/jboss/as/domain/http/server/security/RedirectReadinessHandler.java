/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.as.domain.http.server.security;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.util.function.Function;

import org.jboss.as.domain.http.server.Common;

/**
 * A readiness filter to redirect users to an error page if the realm is not ready to handle authentication requests.
 *
 * @author <a href="mailto:darran.lofthouse@jboss.com">Darran Lofthouse</a>
 */
public class RedirectReadinessHandler extends RealmReadinessHandler {

    private final String redirectTo;

    public RedirectReadinessHandler(final Function<HttpServerExchange, Boolean> readyFunction, final HttpHandler next, final String redirectTo) {
        super(readyFunction, next);
        this.redirectTo = redirectTo;
    }

    /**
     * @see RealmReadinessHandler#rejectRequest(HttpServerExchange)
     */
    @Override
    void rejectRequest(HttpServerExchange exchange) throws Exception {
        exchange.getResponseHeaders().add(Headers.LOCATION, redirectTo);

        Common.TEMPORARY_REDIRECT.handleRequest(exchange);
    }
}