package com.jale.weblog.user.api.dataobject;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class User implements Serializable {

    private Long id;

    private String userName;

    private String password;

    private String salt;

    private String role;

    private String perms;

    /**
     * 这是b1分支
     */

}
