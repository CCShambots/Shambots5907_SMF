package frc.robot.util.Shambots5907_SMF;


public class SimpleTransition<E extends Enum<E>> {

    private E startState;
    private E endState;
    private E interruptionState;

    public SimpleTransition(E startState, E endState, E interruptionState) {
        this.startState = startState;
        this.endState = endState;
        this.interruptionState = interruptionState;
    }

    /**
     * constructor for a transition that can be used for comparing transiitons(i.e basically storing states as a tuple instead of actually including a command)
     * @param startState beginning state
     * @param endState ending state
     */
    public SimpleTransition(E startState, E endState) {
        this(startState, endState, startState);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof SimpleTransition)) return false;
        SimpleTransition compare = (SimpleTransition) obj;
        return compare.startState == this.startState && compare.endState == this.endState && compare.interruptionState == this.interruptionState;
    }

}
