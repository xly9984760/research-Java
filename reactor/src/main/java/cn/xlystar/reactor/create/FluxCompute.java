package cn.xlystar.reactor.create;

import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * 项目名称：research-Java
 * 类 名 称：FluxCompute
 * 类 描 述：TODO
 * 创建时间：2021/12/21 3:33 下午
 * 创 建 人：growingio
 */
public class FluxCompute {
    public static void main(String[] args) throws InterruptedException {
        computeFluxConcat();
        computeFluxMerge();
    }

    public static void computeFluxConcat() throws InterruptedException {
        Flux.concat(
                Flux.range(1000, 20).delayElements(Duration.ofMillis(100)),
                Flux.range(10, 20).delayElements(Duration.ofMillis(100))
        ).subscribe(data -> System.out.print(data+","));
        Thread.sleep(10000);
        System.out.println();
    }

    public static void computeFluxMerge() throws InterruptedException {
        Flux.merge(
                Flux.range(1000, 20).delayElements(Duration.ofMillis(100)),
                Flux.range(10, 20).delayElements(Duration.ofMillis(100))
        ).subscribe(data -> System.out.print(data+","));
        Thread.sleep(10000);
        System.out.println();
    }
}
