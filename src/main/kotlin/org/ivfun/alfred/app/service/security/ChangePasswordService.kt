package org.ivfun.alfred.app.service.security

import org.ivfun.alfred.app.service.security.dto.ChangePassword

interface ChangePasswordService
{

    fun change(changePassword: ChangePassword): MutableMap<String, Any>
}
