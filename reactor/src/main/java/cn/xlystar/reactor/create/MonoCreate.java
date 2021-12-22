package cn.xlystar.reactor.create;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

/**
 * 项目名称：research-Java
 * 类 名 称：MonoCreate
 * 类 描 述：TODO
 * 创建时间：2021/12/21 1:34 下午
 * 创 建 人：growingio
 */
public class MonoCreate {

    public static void main(String[] args) throws InterruptedException {
        createMonoJustString();
        createMonoEmety();
        createMonoFromCallable();
        createMonoFromRunnable();
        createMonoFromFuture();
        createMonoSupplier();

        Disposable disposable = Flux.interval(Duration.ofMillis(50))
                .subscribe(
                        data -> System.out.println("onNext:" + data + ",Thread:" + Thread.currentThread())
                );
        Thread.sleep(1000);
        // 主线程取消订阅
        System.out.println("Thread:" + Thread.currentThread());
        disposable.dispose();
    }

    public static void createMonoJustString() {
        final Mono<Object> just = Mono.just("yes");
        just.subscribe(data -> System.out.println("onNext:" + data + ",Thread:" + Thread.currentThread()));
    }

    public static void createMonoEmety() {
        final Mono<Object> objectMono = Mono.justOrEmpty(null);
        objectMono.subscribe(data -> System.out.println("onNext:" + data + ",Thread:" + Thread.currentThread()));
    }

    /**
     * @Name: createMonoFromCallable
     * @Description: 异步调用封装
     * @Return: void
     * @Date: 2021/12/21 2:10 下午
     * @Auther: growingio
     */
    public static void createMonoFromCallable() {
        Mono.fromCallable(() -> {
            URL url = new URL("https://baidu.cn");
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(inputStream));
            String tmp = null;
            StringBuffer stringBuffer = new StringBuffer();
            while ((tmp = reader.readLine()) != null) {
                stringBuffer.append(tmp).append("\r\n");
            }
            System.out.println(":Callable:" + Thread.currentThread());
            return stringBuffer.toString();
        }).subscribeOn(Schedulers.single()).subscribe(data -> System.out.println("onNext:" + data + ",Thread:" + Thread.currentThread()));
    }

    public static void createMonoFromRunnable() {
        Mono.fromRunnable(() -> {
            try {
                final long l = System.currentTimeMillis();
                System.out.print(l + ":");
                Thread.sleep(1000);
                final long l1 = System.currentTimeMillis();
                System.out.print(l1+",");
                System.out.print(l1 - l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(":Runnable:" + Thread.currentThread());
        }).subscribe(data -> System.out.println("onNext:" + data + ",Thread:" + Thread.currentThread()));
    }

    public static void createMonoFromFuture() {
        Mono.fromFuture(() -> {
            return CompletableFuture.completedFuture("1");
        }).subscribe(System.out::println);
    }

    public static void createMonoSupplier() {
        Mono.fromSupplier(() -> {

            return new int[]{1,2,3,4};
        }).doOnNext(System.out::print).subscribe();
    }
}
