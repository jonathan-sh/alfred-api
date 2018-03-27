package org.ivfun.alfred.integration.slack.message

/**
 * Created by: jonathan
 * DateTime: 2018-03-22 12:19
 **/
data class Attachment
(
        val author_icon: String? = null,
        val author_link: String? = null,
        val author_name: String? = null,
        val color: String? = null,
        val title: String? = null,
        val title_link: String? = null,
        val fields: MutableList<Field> = mutableListOf()
)










