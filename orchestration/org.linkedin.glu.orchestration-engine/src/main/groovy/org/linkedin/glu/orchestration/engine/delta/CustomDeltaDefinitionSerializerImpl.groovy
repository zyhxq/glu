/*
 * Copyright (c) 2011 Yan Pujante
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.linkedin.glu.orchestration.engine.delta

import org.linkedin.groovy.util.json.JsonUtils
import org.linkedin.util.annotations.Initializable

/**
 * @author yan@pongasoft.com */
public class CustomDeltaDefinitionSerializerImpl implements CustomDeltaDefinitionSerializer
{
  public static final int LATEST_CONTENT_VERSION = 1

  @Initializable
  int prettyPrintIndent = 2

  @Override
  int getContentVersion()
  {
    return LATEST_CONTENT_VERSION
  }

  @Override
  String serialize(CustomDeltaDefinition cdd)
  {
    return serialize(cdd, false)
  }

  @Override
  String serialize(CustomDeltaDefinition cdd, boolean prettyPrint)
  {
    def representation = cdd?.toExternalRepresentation()

    if(prettyPrint)
      return JsonUtils.prettyPrint(representation, prettyPrintIndent)
    else
      return JsonUtils.compactPrint(representation)
  }

  @Override
  CustomDeltaDefinition deserialize(String content, int contentVersion)
  {
    if(content == null)
      return null
    
    // currently only 1 version so easy to implement
    if(contentVersion != LATEST_CONTENT_VERSION)
      throw new IllegalArgumentException("unsupported version ${contentVersion}")

    return CustomDeltaDefinition.fromExternalRepresentation(JsonUtils.fromJSON(content))
  }
}