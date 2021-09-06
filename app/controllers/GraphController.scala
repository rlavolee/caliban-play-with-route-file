package controllers

import example.ExampleService
import example.ExampleApi
import example.ExampleData.sampleCharacters

import akka.stream.Materializer
import caliban.PlayRouter
import javax.inject.Inject
import zio.blocking.Blocking
import zio.clock.Clock
import zio.console.Console
import zio.internal.Platform
import zio.random.Random
import zio.Runtime

import play.api.mvc.{ControllerComponents, DefaultActionBuilder, DefaultControllerComponents}
import play.api.routing.SimpleRouter

class GraphController @Inject()(cc: ControllerComponents, mat: Materializer) extends SimpleRouter {

  implicit private val runtime: Runtime.Managed[ExampleService.ExampleService with Random with Blocking with Console with Clock] =
    Runtime.unsafeFromLayer(ExampleService.make(sampleCharacters) ++ Random.live ++ Blocking.live ++ Console.live ++ Clock.live, Platform.default)

  private val interpreter = runtime.unsafeRun(ExampleApi.api.interpreter)

  private val defautController = DefaultControllerComponents(
    DefaultActionBuilder(cc.actionBuilder.parser)(cc.executionContext),
    cc.parsers,
    cc.messagesApi,
    cc.langs,
    cc.fileMimeTypes,
    cc.executionContext
  )

  def routes = PlayRouter(
    interpreter,
    defautController
  )(runtime, mat).routes
}
