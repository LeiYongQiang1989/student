package com.student.core;


/**
 * @author wenhui
 */
public class ResultGenerator {
    private static final String DEFAULT_OK_MESSAGE = "操作成功";
    private static final String DEFAULT_UNAUTHORIZED_MESSAGE = "未认证";
    private static final String DEFAULT_METHOD_NOT_ALLOWED_MESSAGE = "请求方法不允许";

    public ResultGenerator() {
    }

    public static Result genOkResult() {
        return (new Result.Builder(200)).msg("操作成功").success(true).build();
    }

    public static Result genOkResult(final Object data) {
        return (new Result.Builder(200)).msg("操作成功").success(true).rows(data).build();
    }

    public static Result genOkResult(final String msg, final Object data) {
        return (new Result.Builder(200)).msg(msg).success(true).rows(data).build();
    }

    public static Result genFailedResult(final int code, final String msg, final String resultCode, final Object data) {
        return (new Result.Builder(code)).msg(msg).resultCode(resultCode).success(false).rows(data).build();
    }

    public static Result genFailedResult(final int code, final String msg, final String resultCode) {
        return (new Result.Builder(code)).msg(msg).resultCode(resultCode).success(false).build();
    }

    public static Result genFailedResult(final String msg, final String resultCode, final Object data) {
        return (new Result.Builder(500)).msg(msg).resultCode(resultCode).success(false).rows(data).build();
    }

    public static Result genFailedResult(final String msg, final String resultCode) {
        return (new Result.Builder(500)).msg(msg).resultCode(resultCode).success(false).build();
    }

    public static Result gen404FailedResult(final String msg, final String resultCode) {
        return (new Result.Builder(404)).msg(msg).resultCode(resultCode).success(false).build();
    }

    public static Result genBadRequestFailedResult(final String msg, final String resultCode) {
        return (new Result.Builder(400)).msg(msg).resultCode(resultCode).success(false).build();
    }

    public static Result genFailedResult(final String msg) {
        return (new Result.Builder(500)).msg(msg).success(false).build();
    }

    public static Result genMethodErrorResult() {
        return (new Result.Builder(405)).msg("请求方法不允许").success(false).build();
    }

    public static Result genUnauthorizedResult() {
        return (new Result.Builder(401)).msg("未认证").success(false).build();
    }

    public static Result genUnauthorizedResult(final String msg, final String resultCode) {
        return (new Result.Builder(401)).msg(msg).resultCode(resultCode).success(false).build();
    }

    public static Result genUnauthorizedResult(final String msg) {
        return (new Result.Builder(401)).msg(msg).success(false).build();
    }

    public static Result genInternalServerErrorResult(final String url) {
        return (new Result.Builder(500)).msg("API [" + url + "] internal server error. Please call engineer to debug.").success(false).build();
    }
}
