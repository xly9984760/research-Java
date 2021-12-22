package cn.xlystar.reactor.create;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.util.Arrays;

/**
 * 项目名称：research-Java
 * 类 名 称：FluxCreate
 * 类 描 述：TODO
 * 创建时间：2021/12/21 12:08 下午
 * 创 建 人：growingio
 */
public class FluxCreate {

    public static void main(String[] args) {
        createFluxJustString();
        createFluxFromArray();
        createFluxFromIterable();
        createFluxRange();
    }

    /**
     * @Name: createFluxJustString
     * @Description: string作为Flux源
     * @Return: void
     * @Date: 2021/12/21 1:33 下午
     * @Auther: growingio
    */
    public static void createFluxJustString() {
        final Flux<String> stringFlux = Flux.just("String");
        stringFlux.subscribe(System.out::print);
        System.out.println();
    }

    /**
     * @Name: createFluxFromArray
     * @Description: 数组对象作为Flux源
     * @Return: void
     * @Date: 2021/12/21 1:32 下午
     * @Auther: growingio
    */
    public static void createFluxFromArray() {
        final Flux<Integer> arrayFlux = Flux.fromArray(new Integer[]{1, 3, 4, 5});
        arrayFlux.subscribe(System.out::print);
        System.out.println();
    }

    /**
     * @Name: createFluxFromIterable
     * @Description: 迭代对象作为Flux源
     * @Return: void
     * @Date: 2021/12/21 1:31 下午
     * @Auther: growingio
    */
    public static void createFluxFromIterable() {
        final Flux<Integer> iterableFlux = Flux.fromIterable(Arrays.asList(1,3,5,7));
        iterableFlux.subscribe(System.out::print);
        System.out.println();
    }

    /**
     * @Name: createFluxRange
     * @Description: range使用
     * @Return: void
     * @Date: 2021/12/21 1:31 下午
     * @Auther: growingio
    */
    public static void createFluxRange() {
        // 第一个参数是起点，第二个参数表示序列中元素的数量
        final Flux<Integer> rangeFlux = Flux.range(1000,5);
        rangeFlux.subscribe(x -> {
            System.out.print(x+",");
        });
        System.out.println();
    }

    public static void createFlux() {

    }

}
