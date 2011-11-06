using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using ProtoBuf;

namespace ProtoBuf.JodaTime.Tests
{
    [ProtoContract]
    public class People
    {
        [ProtoMember(1, IsRequired = false)]
        public string Name { get; set; }

        [ProtoMember(2, IsRequired = false)]
        public DateTime Date { get; set; }

        [ProtoMember(3, IsRequired = false)]
        public double Size { get; set; }
    }
}
