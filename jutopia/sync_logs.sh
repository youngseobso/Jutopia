#!/bin/bash
while true; do
    inotifywait -e modify /var/lib/docker/containers/*/*.log 2>>/tmp/inotify_errors.log
    if [ $? -ne 0 ]; then
        echo "inotifywait exited with error code $?" >> /tmp/inotify_errors.log
    fi
    rsync -av /var/lib/docker/containers/*/*.log /home/ubuntu/S09P22C108/jutopia/shared/logs/
done

