package wtf.taksa.usual.utils.math;

/**
 * Автор: NoCap
 * Дата создания: 02.07.2025
 */
public record Radius(float topLeft, float topRight, float bottomRight, float bottomLeft) {

    public Radius(float allCorners) {
        this(allCorners, allCorners, allCorners, allCorners);
    }
}