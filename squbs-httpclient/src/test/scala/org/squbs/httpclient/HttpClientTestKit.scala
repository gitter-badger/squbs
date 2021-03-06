/*
 *  Copyright 2015 PayPal
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.squbs.httpclient

import org.squbs.httpclient.endpoint.EndpointRegistry
import org.squbs.httpclient.env.EnvironmentRegistry
import akka.actor.ActorSystem
import akka.io.IO
import spray.can.Http
import akka.pattern._
import scala.concurrent.duration._
import spray.util._

trait HttpClientTestKit {

  implicit val system: ActorSystem

  def clearHttpClient() = {
    EndpointRegistry(system).endpointResolvers.clear()
    EnvironmentRegistry(system).environmentResolvers.clear()
    HttpClientManager(system).httpClientMap.clear()
  }

  def shutdownActorSystem() = {
    IO(Http).ask(Http.CloseAll)(30.seconds).await
    system.shutdown()
  }
}