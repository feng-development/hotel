/*
 *  Copyright 1999-2019 Evision Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.feng.hotel.utils.date;

import java.util.Date;


public enum DatePattern {

    DATE_MONTH {
        @Override
        public String pattern() {
            return "yyyy-MM";
        }
    },

    DATE {
        @Override
        public String pattern() {
            return "yyyy-MM-dd";
        }
    },

    DATETIME {
        @Override
        public String pattern() {
            return "yyyy-MM-dd HH:mm:ss";
        }
    },

    TIME {
        @Override
        public String pattern() {
            return "HH:mm:ss";
        }
    };

    public abstract String pattern();

    public String format(Date date) {
        return DateUtils.format(date, pattern());
    }

    public Date parse(String input) {
        return DateUtils.parse(input, pattern());
    }

}
