package com.example.mvicompose.rx

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class FakeScheduleProvider : SchedulerProvider {

    override fun computation(): Scheduler = Schedulers.trampoline()

    override fun io(): Scheduler = Schedulers.trampoline()

    override fun ui(): Scheduler = Schedulers.trampoline()
}