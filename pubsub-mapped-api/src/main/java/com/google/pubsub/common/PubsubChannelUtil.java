/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.pubsub.common;

import com.google.auth.oauth2.GoogleCredentials;
import io.grpc.auth.MoreCallCredentials;
import io.grpc.CallCredentials;
import io.grpc.Channel;
import io.grpc.ClientInterceptors;
import io.grpc.ManagedChannel;
import io.grpc.auth.ClientAuthInterceptor;
import io.grpc.internal.ManagedChannelImpl;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

public class PubsubChannelUtil {

  private static final String ENDPOINT = "pubsub.googleapis.com";
  private static final List<String> CPS_SCOPE = Arrays.asList("https://www.googleapis.com/auth/pubsub");

  public static final String CPS_TOPIC_FORMAT = "projects/%s/topics/%s";
  public static final String KEY_ATTRIBUTE = "key";

  private ManagedChannel channel;
  private CallCredentials callCredentials;

  public PubsubChannelUtil() throws IOException {
    GoogleCredentials credentials = GoogleCredentials.getApplicationDefault().createScoped(CPS_SCOPE);
    callCredentials = MoreCallCredentials.from(credentials);
    channel = NettyChannelBuilder.forAddress(ENDPOINT, 443).negotiationType(NegotiationType.TLS).build();
  }

  public CallCredentials callCredentials() {
    return callCredentials;
  }

  public Channel channel() {
    return channel;
  }

  public void closeChannel() {
    channel.shutdown();
  }
}