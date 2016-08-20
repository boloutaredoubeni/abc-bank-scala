package com.boloutaredoubeni.bank

import java.util.{Calendar, Date}

object DateProvider {
  def getInstance: DateProvider = {
    if (instance == null) instance = new DateProvider
    instance
  }

  private var instance: DateProvider = null
}

class DateProvider {
  def now: Date = {
    return Calendar.getInstance.getTime
  }
}
