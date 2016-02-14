package com.neversink.gank.model.db;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.NotNull;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Unique;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * Created by never on 16/1/25.
 */
public class Soul implements Serializable {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("_id")
    public long id;
    @NotNull
    @Unique
    @Column("objectId")
    public String objectId;
}
