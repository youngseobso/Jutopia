
while inotifywait -e modify /var/lib/docker/containers/*/*.log; do
    rsync -av /var/lib/docker/containers/*/*.log /home/ubuntu/S09P22C108/jutopia/shared/logs/
done
