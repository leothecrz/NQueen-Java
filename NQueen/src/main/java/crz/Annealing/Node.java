package crz.Annealing;

public class Node 
{

    private int AttackingCount;
    private int QueenValue;
    private int MoveToIndex;

    public Node(int AC, int QV, int MTI)
    {
        AttackingCount = AC;
        QueenValue = QV;
        MoveToIndex = MTI;
    }

    public int getAttackingCount() 
    {
        return AttackingCount;
    }

    public int getMoveToIndex() 
    {
        return MoveToIndex;
    }

    public int getQueenValue() 
    {
        return QueenValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AC: " + AttackingCount + " ");
        sb.append("QV: " + QueenValue + " ");
        sb.append("MI: " + MoveToIndex+ " ");
        return sb.toString();
    }
    
}
