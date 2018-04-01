package org.ivfun.alfred.app.usefull.enuns

/**
 * Created by: jonathan
 * DateTime: 2018-03-22 17:33
 **/
enum class BuildStatus
{
    WAITING,
    DISCARDED,
    BUILDING_ARTIFACT,
    BUILD_COMPLETED,
    SENDING_ARTIFACT,
    RESTARTING_SERVICE,
    IN_DEPLOY,
    FAIL
}
