# Publisher Subscriber

- The publisher creates work items for the subscriber and submits them
- The subscriber manages a thread pool and executes the work items, the threads must not sleep but park themselves
- The publisher un-parks the threads when there is more work available.

 