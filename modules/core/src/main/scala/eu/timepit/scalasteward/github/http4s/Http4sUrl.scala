/*
 * Copyright 2018 scala-steward contributors
 *
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

package eu.timepit.scalasteward.github.http4s

import cats.implicits._
import eu.timepit.scalasteward.git.Branch
import eu.timepit.scalasteward.github.Url
import eu.timepit.scalasteward.github.data.Repo
import eu.timepit.scalasteward.util.ApplicativeThrowable
import eu.timepit.scalasteward.util.uri._
import org.http4s.Uri

class Http4sUrl(apiHost: String) {
  val url = new Url(apiHost)

  def branches[F[_]: ApplicativeThrowable](repo: Repo, branch: Branch): F[Uri] =
    fromString[F](url.branches(repo, branch))

  def forks[F[_]: ApplicativeThrowable](repo: Repo): F[Uri] =
    fromString[F](url.forks(repo))

  def listPullRequests[F[_]: ApplicativeThrowable](repo: Repo, head: String): F[Uri] =
    pulls[F](repo).map(_.withQueryParam("head", head).withQueryParam("state", "all"))

  def pulls[F[_]: ApplicativeThrowable](repo: Repo): F[Uri] =
    fromString[F](url.pulls(repo))
}
