package com.nixum.webAdmin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nixum.authentication.jwt.JwtUtil;
import com.nixum.authentication.service.SecurityUserDetailsService;
import com.nixum.common.constant.RedisConstant;
import com.nixum.common.redis.JedisUtil;
import com.nixum.common.response.Result;
import com.nixum.common.response.ResultGenerator;
import com.nixum.model.model.auth.User;
import com.nixum.webAdmin.service.IUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * @author Zoctan
 * @date 2018/06/09
 * 后台用户控制器
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Resource
    private IUserService userService;
    @Resource
    private SecurityUserDetailsService userDetailsService;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private JedisUtil jedisUtil;

    @PostMapping
    public Result register(@RequestBody @Valid final User user,
                           final BindingResult bindingResult) {
        // {"username":"123456", "password":"123456", "email": "123456@qq.com"}
        if (bindingResult.hasErrors()) {
            //noinspection ConstantConditions
            final String msg = bindingResult.getFieldError().getDefaultMessage();
            return ResultGenerator.genFailedResult(msg);
        } else {
            this.userService.save(user);
            return this.getToken(user);
        }
    }

    @PreAuthorize("hasAuthority('user:delete')")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable final Long id) {
        this.userService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PostMapping("/password")
    public Result validatePassword(@RequestBody final User user) {
        final User oldUser = this.userService.findById(user.getId());
        final boolean isValidate = this.userService.verifyPassword(user.getPassword(), oldUser.getPassword());
        return ResultGenerator.genOkResult(isValidate);
    }

    @PutMapping
    public Result update(@RequestBody final User user) {
        this.userService.update(user);
        return this.getToken(this.userService.findById(user.getId()));
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable final Long id) {
        final User user = this.userService.findById(id);
        return ResultGenerator.genOkResult(user);
    }

    @GetMapping("/info")
    public Result info(final Principal user) {
        final User userDB = this.userService.findDetailByUsername(user.getName());
        return ResultGenerator.genOkResult(userDB);
    }

    @GetMapping("/userInfo")
    public Result userInfo(final Principal user) {
        User userDB = this.userService.findDetailBy("username", user.getName());
        if (userDB!=null){
            userDB.setPassword(null);
        }
        return ResultGenerator.genOkResult(userDB);
    }

    @PreAuthorize("hasAuthority('user:list')")
    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") final Integer page,
                       @RequestParam(defaultValue = "0") final Integer size) {
        PageHelper.startPage(page, size);
        final List<User> list = this.userService.findAllUserWithRole();
        //noinspection unchecked
        final PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @PostMapping("/login")
    public Result login(@RequestBody final User user) {

        if (user.getUsername() == null && user.getEmail() == null) {
            return ResultGenerator.genFailedResult("username or email empty");
        }
        if (user.getPassword() == null) {
            return ResultGenerator.genFailedResult("password empty");
        }
        // 用户名登录
        User dbUser = null;
        if (user.getUsername() != null) {
            dbUser = this.userService.findBy("username", user.getUsername());
            if (dbUser == null) {
                return ResultGenerator.genFailedResult("username error");
            }
        }
        // 邮箱登录
        if (user.getEmail() != null) {
            dbUser = this.userService.findBy("email", user.getEmail());
            if (dbUser == null) {
                return ResultGenerator.genFailedResult("email error");
            }
            user.setUsername(dbUser.getUsername());
        }
        // 验证密码
        if (!this.userService.verifyPassword(user.getPassword(), dbUser.getPassword())) {
            return ResultGenerator.genFailedResult("password error");
        }
        // 更新登录时间
        this.userService.updateLoginTimeByUsername(user.getUsername());
        return this.getToken(user);
    }

    @GetMapping("/logout")
    public Result logout(final Principal user) {
        jedisUtil.StringProcessor.del(user.getName());
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/obsolete")
    public Result obsolete() {
        return ResultGenerator.genUnauthorizedResult("登录过时了，请重新登录");
    }

    /**
     * 获得 token
     */
    private Result getToken(final User user) {
        final String username = user.getUsername();
        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        final String token = this.jwtUtil.sign(username, userDetails.getAuthorities());
        // 以username为key，token为value放入redis中
        jedisUtil.StringProcessor.setEx(username, token, RedisConstant.EXPIRE_NINETY);
        return ResultGenerator.genOkResult(token);
    }
}
