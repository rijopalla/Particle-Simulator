class Particle {
    double x, y, vx, vy;
    double radius = 5;

    public Particle(double x, double y, double velocity, double angleInDegrees) {
        this.x = x;
        this.y = y;
        double angleInRadians = Math.toRadians(angleInDegrees);
        this.vx = velocity * Math.cos(angleInRadians);
        this.vy = velocity * Math.sin(angleInRadians);
    }

    public void updatePosition() {
        x += vx;
        y += vy;
    }

    public void bounceOffWalls(int width, int height) {
        if (x - radius <= 0 || x + radius >= width) {
            vx *= -1; // Reverse horizontal direction
        }
        if (y - radius <= 0 || y + radius >= height) {
            vy *= -1; // Reverse vertical direction
        }
    }
}
