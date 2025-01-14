package net.suqatri.redicloud.node.setup.group;

import lombok.Getter;
import net.suqatri.redicloud.api.service.ServiceEnvironment;
import net.suqatri.redicloud.node.NodeLauncher;
import net.suqatri.redicloud.node.console.setup.Setup;
import net.suqatri.redicloud.node.console.setup.SetupHeaderBehaviour;
import net.suqatri.redicloud.node.console.setup.annotations.AnswerCompleter;
import net.suqatri.redicloud.node.console.setup.annotations.ConditionChecker;
import net.suqatri.redicloud.node.console.setup.annotations.Question;
import net.suqatri.redicloud.node.console.setup.annotations.RequiresEnum;
import net.suqatri.redicloud.node.console.setup.conditions.PositivIntegerCondition;
import net.suqatri.redicloud.node.console.setup.suggester.BooleanSuggester;
import net.suqatri.redicloud.node.setup.condition.GroupMemoryCondition;
import net.suqatri.redicloud.node.setup.condition.ServiceVersionExistsCondition;
import net.suqatri.redicloud.node.setup.suggester.ServiceEnvironmentSuggester;

@Getter
public class GroupSetup extends Setup<GroupSetup> {

    @RequiresEnum(ServiceEnvironment.class)
    @Question(id = 1, question = "Which type of group do you want to create?")
    @AnswerCompleter(value = ServiceEnvironmentSuggester.class)
    private ServiceEnvironment environment;

    @Question(id = 2, question = "How many service of this group should be online all the time?")
    @ConditionChecker(value = PositivIntegerCondition.class, message = "The number of services must be positive or zero.")
    private int minServices;

    @Question(id = 3, question = "How many service of this group should be online at most?")
    private int maxServices;

    @Question(id = 4, question = "How much memory should be allocated to each service?")
    @ConditionChecker(value = GroupMemoryCondition.class, message = "The memory must be higher than 500")
    private int maxMemory;

    @Question(id = 5, question = "What is the start priority of the group?")
    private int startPriority;

    @Question(id = 6, question = "Should the group a static group?")
    @AnswerCompleter(value = BooleanSuggester.class)
    private boolean staticGroup;

    @Question(id = 7, question = "Should the group be a fallback group?")
    @AnswerCompleter(value = BooleanSuggester.class)
    private boolean fallback;

    @Question(id = 8, question = "What service version should be used for the group?")
    @ConditionChecker(value = ServiceVersionExistsCondition.class, message = "Service version does not exist.")
    private String serviceVersionName;

    public GroupSetup() {
        super(NodeLauncher.getInstance().getConsole());
    }

    @Override
    public boolean isCancellable() {
        return true;
    }

    @Override
    public boolean shouldPrintHeader() {
        return true;
    }

    @Override
    public SetupHeaderBehaviour headerBehaviour() {
        return SetupHeaderBehaviour.RESTORE_PREVIOUS_LINES;
    }
}
