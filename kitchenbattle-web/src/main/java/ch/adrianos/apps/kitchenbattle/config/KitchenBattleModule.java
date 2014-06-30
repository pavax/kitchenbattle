package ch.adrianos.apps.kitchenbattle.config;

import ch.adrianos.apps.kitchenbattle.domain.battle.BattleId;
import ch.adrianos.apps.kitchenbattle.domain.battle.CourseBattle;
import ch.adrianos.apps.kitchenbattle.domain.course.Course;
import ch.adrianos.apps.kitchenbattle.domain.course.CourseId;
import ch.adrianos.apps.kitchenbattle.domain.team.Team;
import ch.adrianos.apps.kitchenbattle.domain.team.TeamId;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class KitchenBattleModule extends SimpleModule {

    public KitchenBattleModule() {
        super("Kitchen Battle Domain Mixins", new Version(1, 0, 0, null, "ch.adrianos.apps.kitchenbattle", "domain"));
        setMixInAnnotation(BattleId.class, BattleIdMixin.class);
        setMixInAnnotation(CourseId.class, CourseIdMixin.class);
        setMixInAnnotation(TeamId.class, TeamIdMixin.class);
        setMixInAnnotation(CourseBattle.class, UnwrapDomainIdsMixin.class);
        setMixInAnnotation(Team.class, UnwrapDomainIdsMixin.class);
        setMixInAnnotation(Course.class, UnwrapDomainIdsMixin.class);
    }

    static abstract class BattleIdMixin {
        @JsonProperty("battleId")
        private String value;
    }
    static abstract class TeamIdMixin {
        @JsonProperty("teamId")
        private String value;
    }
    static abstract class CourseIdMixin {
        @JsonProperty("courseId")
        private String value;
    }

    static abstract class UnwrapDomainIdsMixin {
        @JsonUnwrapped
        private BattleId battleId;

        @JsonUnwrapped
        private CourseId courseId;

        @JsonUnwrapped
        private TeamId teamId;

        @JsonUnwrapped(suffix = "One")
        private CourseId courseOneId;

        @JsonUnwrapped(suffix = "Two")
        private CourseId courseTwoId;
    }
}
