/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.servicecomb.samples.porter.gateway;

import org.apache.servicecomb.core.Handler;
import org.apache.servicecomb.core.Invocation;
import org.apache.servicecomb.swagger.invocation.AsyncResponse;
import org.apache.servicecomb.swagger.invocation.exception.InvocationException;

public class InternalAccessHandler implements Handler {

  /***
   * 网关实现Hanlder，检查访问的接口是否具备定义的tags，如果具备，认为是内部接口，不允许访问。
   * 检查接口是否定义了名称为”INTERNAL”的tags，如果包含，则不允许访问
   * @param invocation
   * @param asyncReponse
   * @throws Exception
   */
  @Override
  public void handle(Invocation invocation, AsyncResponse asyncReponse) throws Exception {
    if (invocation.getOperationMeta().getSwaggerOperation().getTags() != null
        && invocation.getOperationMeta().getSwaggerOperation().getTags().contains("INTERNAL")) {
      asyncReponse.consumerFail(new InvocationException(403, "", "not allowed"));
      return;
    }
    invocation.next(asyncReponse);
  }

}
