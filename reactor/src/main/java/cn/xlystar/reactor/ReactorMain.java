package cn.xlystar.reactor;

import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.concurrent.*;

/**
 * 项目名称：research-Java
 * 类 名 称：ReactorMain
 * 类 描 述：TODO
 * 创建时间：2021/12/21 12:07 下午
 * 创 建 人：growingio
 */
public class ReactorMain {


    public static void main(String[] args) throws InterruptedException {

        final ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(10), Executors.defaultThreadFactory());


            Flux.range(1,300).map(x -> {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Map1 -> " + Thread.currentThread() + x);
                        return x;
                    })
                    .subscribeOn(Schedulers.single()).map(x -> {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Map2 -> " + Thread.currentThread() + x);
                        return x;
                    }).publishOn(Schedulers.parallel()).map(x -> {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Map3 -> " + Thread.currentThread() + x);
                        return x;
                    }).subscribe();

            Thread.sleep(10000000);

//            Flux.fromIterable(Arrays.asList(1, 2, 3)).subscribeOn(Schedulers.single()).subscribe();

    }
}

class SampleSubscriber<T> extends BaseSubscriber<T> {

    @Override
    public void hookOnSubscribe(Subscription subscription) {
        System.out.println("Subscribed");
        request(1);
    }

    @Override
    public void hookOnNext(T value) {
        System.out.println(value);
        request(1);
    }
}