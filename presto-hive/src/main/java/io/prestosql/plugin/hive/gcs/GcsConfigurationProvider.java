/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.prestosql.plugin.hive.gcs;

import com.google.cloud.hadoop.gcsio.GoogleCloudStorageFileSystem;
import io.prestosql.plugin.hive.DynamicConfigurationProvider;
import org.apache.hadoop.conf.Configuration;

import java.net.URI;

import static io.prestosql.plugin.hive.HdfsEnvironment.HdfsContext;
import static io.prestosql.plugin.hive.gcs.GcsAccessTokenProvider.GCS_ACCESS_TOKEN_CONF;

public class GcsConfigurationProvider
        implements DynamicConfigurationProvider
{
    private static final String GCS_OAUTH_KEY = "hive.gcs.oauth";

    @Override
    public void updateConfiguration(Configuration configuration, HdfsContext context, URI uri)
    {
        if (!uri.getScheme().equals(GoogleCloudStorageFileSystem.SCHEME)) {
            return;
        }

        String accessToken = context.getIdentity().getExtraCredentials().get(GCS_OAUTH_KEY);
        if (accessToken != null) {
            configuration.set(GCS_ACCESS_TOKEN_CONF, accessToken);
        }
    }
}
